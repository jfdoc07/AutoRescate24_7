package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * Clase central que contiene toda la lógica y estructuras de datos del sistema
 * AutoRescate 24/7.
 *
 * @author Santiago Aroca, Fernando Cárdenas, Daniel Molano
 */
public class Sistema {

    private Cola<SolicitudServicio> colaOrdinaria;
    private ColaPrioridad colaCritica;
    private Pila<Operacion> historial;
    private Pila<Kit> pilaKits;
    private ListaEnlazadaSimple<UnidadServicio> listaUnidades;
    private ListaEnlazadaSimple<Tecnico> listaTecnicos;
    private ListaEnlazadaSimple<Kit> listaKits;
    private ListaEnlazadaSimple<Cliente> listaClientes;
    private ListaEnlazadaSimple<SolicitudServicio> listaEnEjecucion;
    private ListaEnlazadaSimple<SolicitudServicio> listaCerradas;

    public Sistema() {
        colaOrdinaria = new Cola<>();
        colaCritica = new ColaPrioridad();
        historial = new Pila<>();
        pilaKits = new Pila<>();
        listaUnidades = new ListaEnlazadaSimple<>();
        listaTecnicos = new ListaEnlazadaSimple<>();
        listaKits = new ListaEnlazadaSimple<>();
        listaClientes = new ListaEnlazadaSimple<>();
        listaEnEjecucion = new ListaEnlazadaSimple<>();
        listaCerradas = new ListaEnlazadaSimple<>();
    }

    public void añadirUnidad(TipoUnidad tipo, Zona zona) {
        UnidadServicio unidad = new UnidadServicio(tipo, EstadoUnidad.ACTIVO, zona, true);
        listaUnidades.agregar(unidad);
    }

    public ListaEnlazadaSimple.Iterador<UnidadServicio> iteradorUnidades() {
        return listaUnidades.iterador();
    }

    public void actualizarUnidad(UnidadServicio unidad, TipoUnidad tipo, EstadoUnidad estado,
            Zona zona, boolean disponibilidad) {
        if (listaUnidades.contiene(unidad)) {
            unidad.setTipo(tipo);
            unidad.setEstado(estado);
            unidad.setZona(zona);
            unidad.setDisponibilidad(disponibilidad);
        }
    }

    public void eliminarUnidad(UnidadServicio unidad) {
        listaUnidades.eliminar(unidad);
    }

    public String enviarSacarMantenimiento(String unidadID) {
        if (unidadID == null) {
            throw new IllegalArgumentException("No se seleccionó ninguna unidad.");
        }
        java.util.UUID idReal = java.util.UUID.fromString(unidadID);
        UnidadServicio u = buscarUnidadPorUuid(idReal);
        if (u == null) {
            throw new IllegalArgumentException("No se encontró la unidad");
        } else {
            switch (u.getEstado()) {
                case MANTENIMIENTO: {
                    EstadoUnidad estadoAnterior = u.getEstado();
                    boolean disponibilidadAnterior = u.isDisponibilidad();
                    u.setEstado(EstadoUnidad.ACTIVO);
                    u.setDisponibilidad(true);
                    historial.apilar(new OperacionCambioEstadoUnidad(u, estadoAnterior, disponibilidadAnterior));
                    return "Unidad sacada de mantenimiento. Recargue la página para ver los cambios.";
                }
                case ACTIVO: {
                    EstadoUnidad estadoAnterior = u.getEstado();
                    boolean disponibilidadAnterior = u.isDisponibilidad();
                    u.setEstado(EstadoUnidad.MANTENIMIENTO);
                    u.setDisponibilidad(false);
                    historial.apilar(new OperacionCambioEstadoUnidad(u, estadoAnterior, disponibilidadAnterior));
                    return "Unidad enviada a mantenimiento";
                }
                default:
                    throw new IllegalArgumentException("La unidad se encuentra ocupada, no se puede enviar a mantenimiento. Recargue la página para ver los cambios.");
            }
        }
    }

    public void añadirTecnico(String nombre, TipoServicio especialidad, boolean disponibilidad, EstadoTecnico estado) {
        Tecnico tecnico = new Tecnico(nombre, especialidad, disponibilidad, estado);
        listaTecnicos.agregar(tecnico);
        System.out.println("Tecnico registrado con exito " + tecnico.getNombre() + " " + tecnico.getEspecialidad() + " " + tecnico.getEstado() + "Recargue la página para ver los cambios.");
    }

    public void actualizarTecnico(Tecnico tecnico, String id, String nombre, TipoServicio especialidad, boolean disponibilidad, EstadoTecnico estado) {
        if (listaTecnicos.contiene(tecnico)) {
            tecnico.setNombre(nombre);
            tecnico.setEspecialidad(especialidad);
            tecnico.setDisponibilidad(disponibilidad);
            tecnico.setEstado(estado);
        }
    }

    public void eliminarTecnico(Tecnico tecnico) {
        listaTecnicos.eliminar(tecnico);
    }

    public ListaEnlazadaSimple.Iterador<Tecnico> iteradorTecnicos() {
        return listaTecnicos.iterador();
    }

    public void registrarSolicitud(Cliente cliente, TipoServicio tipoServicio, String descripcion, Zona zona) {
        SolicitudServicio solicitud = new SolicitudServicio(cliente, tipoServicio, descripcion, NivelCriticidad.ORDINARIA, null, null, null, EstadoSolicitud.PENDIENTE, zona);
        solicitud.setPuntajeCriticidad(solicitud.calcularPuntaje());
        solicitud.setCriticidad(solicitud.calcularNivel());

        if (solicitud.getCriticidad() == NivelCriticidad.CRITICA) {
            colaCritica.insertar(solicitud);
        } else {
            colaOrdinaria.enqueue(solicitud);
        }
    }

    public void atenderSiguienteSolicitud() {
        SolicitudServicio solicitud;

        if (!colaCritica.estaVacia()) {
            solicitud = colaCritica.extraerMaxPrioridad();
        } else if (!colaOrdinaria.estaVacia()) {
            solicitud = colaOrdinaria.dequeue();
        } else {
            return;
        }

        ListaEnlazadaSimple.Iterador<UnidadServicio> itUnidades = iteradorUnidades();
        UnidadServicio unidadDisponible = null;
        while (itUnidades.tieneSiguiente()) {
            UnidadServicio u = itUnidades.siguiente();
            if (u.isDisponibilidad() && u.getEstado() != EstadoUnidad.MANTENIMIENTO
                    && esCompatible(u.getTipo(), solicitud.getTipoServicio())
                    && u.getZona().equals(solicitud.getZona())) {
                unidadDisponible = u;
                break;
            }
        }
        if (unidadDisponible == null) {
            if (solicitud.getCriticidad() == NivelCriticidad.CRITICA) {
                colaCritica.insertar(solicitud);
            } else {
                colaOrdinaria.enqueue(solicitud);
            }
            throw new IllegalArgumentException("No hay unidades disponibles.");
        }

        ListaEnlazadaSimple.Iterador<Tecnico> itTecnicos = iteradorTecnicos();
        Tecnico tecnicoDisponible = null;
        while (itTecnicos.tieneSiguiente()) {
            Tecnico t = itTecnicos.siguiente();
            if (t.isDisponibilidad() && t.getEstado() != EstadoTecnico.ASIGNADO
                    && t.getEspecialidad() == solicitud.getTipoServicio()) {
                tecnicoDisponible = t;
                break;
            }
        }
        if (tecnicoDisponible == null) {
            if (solicitud.getCriticidad() == NivelCriticidad.CRITICA) {
                colaCritica.insertar(solicitud);
            } else {
                colaOrdinaria.enqueue(solicitud);
            }
            throw new IllegalArgumentException("No hay técnicos disponibles.");
        }

        if (pilaKits.estaVacia()) {
            if (solicitud.getCriticidad() == NivelCriticidad.CRITICA) {
                colaCritica.insertar(solicitud);
            } else {
                colaOrdinaria.enqueue(solicitud);
            }
            throw new IllegalArgumentException("No hay kits disponibles.");
        }

        Kit kit = utilizarKit();

        solicitud.setUnidadAsignada(unidadDisponible);
        solicitud.setTecnico(tecnicoDisponible);
        solicitud.setKitAsignado(kit);
        solicitud.setEstado(EstadoSolicitud.EN_EJECUCION);

        unidadDisponible.setDisponibilidad(false);
        unidadDisponible.setEstado(EstadoUnidad.OCUPADO);

        tecnicoDisponible.setDisponibilidad(false);
        tecnicoDisponible.setEstado(EstadoTecnico.ASIGNADO);
        
        historial.apilar(new OperacionAtencionServicio(solicitud, unidadDisponible, tecnicoDisponible, kit));

        listaEnEjecucion.agregar(solicitud);
        System.out.println("Solicitud en proceso");
    }

    public String cerrarSolicitud(String solicitudIdStr) {
        if (solicitudIdStr == null) {
            throw new IllegalArgumentException("No seleccionó ninguna solicitud.");
        }

        System.out.println("=== ANALIZANDO SOLICITUD ===");
        System.out.println("ID que llegó desde la JTable: [" + solicitudIdStr + "]");
        
        java.util.UUID uuidBuscado = java.util.UUID.fromString(solicitudIdStr);
        SolicitudServicio solicitud = listaEnEjecucion.obtenerPorUuid(uuidBuscado);
        
        if (solicitud == null) {
            throw new IllegalArgumentException("No se encontró la solicitud en ejecución.");
        }

        solicitud.setEstado(EstadoSolicitud.CERRADA);

        if (solicitud.getUnidadAsignada() != null) {
            solicitud.getUnidadAsignada().setDisponibilidad(true);
            solicitud.getUnidadAsignada().setEstado(EstadoUnidad.ACTIVO);
        }

        if (solicitud.getTecnico() != null) {
            solicitud.getTecnico().setDisponibilidad(true);
            solicitud.getTecnico().setEstado(EstadoTecnico.DISPONIBLE);
        }

        if (solicitud.getKitAsignado() != null) {
            double probabilidad = Math.random();
            if (probabilidad < 0.8) { // 80% llega completo
                solicitud.getKitAsignado().setCompleto(true);
                solicitud.getKitAsignado().setRequiereReposicion(false);
            } else { // 20% requiere reposición
                solicitud.getKitAsignado().setCompleto(false);
                solicitud.getKitAsignado().setRequiereReposicion(true);
            }
            listaKits.agregar(solicitud.getKitAsignado());
        }

        listaEnEjecucion.eliminar(solicitud);
        listaCerradas.agregar(solicitud);
        historial.apilar(new OperacionCierreServicio(solicitud));
        
        try {
            csv(); // Actualiza el archivo CSV al cerrar
        } catch (IOException e) {
            System.out.println("Error al escribir reporte CSV: " + e.getMessage());
        }

        return "Solicitud cerrada con éxito";
    }

    public Kit añadirKit(String descripcion, boolean completo, boolean requiereReposicion) {
        Kit kit = new Kit(descripcion, completo, requiereReposicion);
        listaKits.agregar(kit);
        return kit;
    }

    public String enviarKitAPila(String idKit) {
        if (idKit == null) {
            throw new IllegalArgumentException("No se seleccionó ningun kit.");
        }
        java.util.UUID idReal = java.util.UUID.fromString(idKit);
        Kit kit = buscarKitPorUuid(idReal);
        if (kit == null) {
            throw new IllegalArgumentException("El kit no existe en el sistema.");
        }
        kit.setCompleto(true);
        kit.setRequiereReposicion(false);
        pilaKits.apilar(kit);
        listaKits.eliminar(kit);
        return "Kit apilado con éxito. Recargue la página para ver los cambios.";
    }

    public Kit utilizarKit() {
        return pilaKits.desapilar();
    }

    public void eliminarKit(Kit kit) {
        listaKits.eliminar(kit);
    }

    public void actualizarKit(Kit kit, UUID id, String descripcion, boolean completo, boolean requiereReposicion) {
        if (pilaKits.contiene(kit)) {
            kit.setId(id);
            kit.setDescripcion(descripcion);
            kit.setCompleto(completo);
            kit.setRequiereReposicion(requiereReposicion);
        }
    }

    public Kit verCimaKits() {
        return pilaKits.verCima();
    }

    public ListaEnlazadaSimple<Kit> getListaKits() {
        return listaKits;
    }

    public void registrarOperacion(Operacion operacion) {
        historial.apilar(operacion);
    }

    public String deshacerOperacion() {
        if (historial == null || historial.estaVacia()) throw new IllegalArgumentException("El historial está vacío.");
        Operacion operacion = historial.verCima();
        historial.desapilar();
        operacion.deshacer(this);
        return "Ultima operación revertida.";
    }

    public Operacion verUltimaOperacion() {
        return historial.verCima();
    }

    public ListaEnlazadaSimple.Iterador<SolicitudServicio> iteradorCerradas() {
        return listaCerradas.iterador();
    }

    public String csv() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("reporte.csv"));
        writer.write("id,cliente,tipoServicio,zona");

        @SuppressWarnings("rawtypes")
        ListaEnlazadaSimple.Iterador<SolicitudServicio> it = iteradorCerradas();

        writer.newLine();
        while (it.tieneSiguiente()) {
            SolicitudServicio s = it.siguiente();
            writer.write(s.getUuid().toString() + "," + s.getCliente() + "," + s.getTipoServicio() + "," + s.getZona());
            writer.newLine();
        }
        writer.close();
        return "Reporte generado con éxito, revisar la carpeta raíz.";
    }

    private boolean esCompatible(TipoUnidad tipoUnidad, TipoServicio tipoServicio) {
        switch (tipoServicio) {
            case GRUA:
                return tipoUnidad == TipoUnidad.GRUA;
            case PASO_CORRIENTE:
                return tipoUnidad == TipoUnidad.MOTO
                        || tipoUnidad == TipoUnidad.CAMIONETA
                        || tipoUnidad == TipoUnidad.VEHICULO_LIVIANO;
            case CAMBIO_LLANTA:
                return tipoUnidad == TipoUnidad.CAMIONETA
                        || tipoUnidad == TipoUnidad.VEHICULO_LIVIANO;
            case APERTURA_PUERTAS:
            case COMBUSTIBLE:
                return tipoUnidad == TipoUnidad.MOTO
                        || tipoUnidad == TipoUnidad.CAMIONETA
                        || tipoUnidad == TipoUnidad.VEHICULO_LIVIANO;
            case REVISION_MECANICA:
                return tipoUnidad == TipoUnidad.CAMIONETA
                        || tipoUnidad == TipoUnidad.VEHICULO_LIVIANO;
            default:
                return false;
        }
    }

    public UnidadServicio buscarUnidadPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null) {
            return null;
        }
        return listaUnidades.obtenerPorUuid(uuidBuscado);
    }

    public Kit buscarKitPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null) {
            return null;
        }
        return listaKits.obtenerPorUuid(uuidBuscado);
    }

    public Tecnico buscarTecnicoPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null) {
            return null;
        }
        return listaTecnicos.obtenerPorUuid(uuidBuscado);
    }

    public Cola<SolicitudServicio> getColaOrdinaria() {
        return colaOrdinaria;
    }

    public ColaPrioridad getColaCritica() {
        return colaCritica;
    }

    public Pila<Operacion> getHistorial() {
        return historial;
    }

    public Pila<Kit> getPilaKits() {
        return pilaKits;
    }

    public ListaEnlazadaSimple<UnidadServicio> getListaUnidades() {
        return listaUnidades;
    }

    public ListaEnlazadaSimple<Tecnico> getListaTecnicos() {
        return listaTecnicos;
    }

    public ListaEnlazadaSimple<Cliente> getListaClientes() {
        return listaClientes;
    }

    public ListaEnlazadaSimple<SolicitudServicio> getListaCerradas() {
        return listaCerradas;
    }

    public ListaEnlazadaSimple<SolicitudServicio> getListaEnEjecucion() {
        return listaEnEjecucion;
    }
}