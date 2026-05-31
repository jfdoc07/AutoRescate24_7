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
        listaCerradas = new ListaEnlazadaSimple<>();
    }

    public void añadirUnidad(TipoUnidad tipo, EstadoUnidad estado, Zona zona, boolean disponibilidad) {
        UnidadServicio unidad = new UnidadServicio(tipo, estado, zona, disponibilidad);
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

    public void enviarAMantenimiento(UnidadServicio unidad) {
        EstadoUnidad estadoAnterior = unidad.getEstado();
        unidad.setEstado(EstadoUnidad.MANTENIMIENTO);
        unidad.setDisponibilidad(false);
        historial.apilar(new OperacionCambioEstadoUnidad(unidad, estadoAnterior));
    }

    public void sacarDeMantenimiento(UnidadServicio unidad) {
        EstadoUnidad estadoAnterior = unidad.getEstado();
        unidad.setEstado(EstadoUnidad.ACTIVO);
        unidad.setDisponibilidad(true);
        historial.apilar(new OperacionCambioEstadoUnidad(unidad, estadoAnterior));
    }

    public void añadirTecnico(String nombre, TipoServicio especialidad, boolean disponibilidad, EstadoTecnico estado) {
        Tecnico tecnico = new Tecnico(nombre, especialidad, disponibilidad, estado);
        listaTecnicos.agregar(tecnico);
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

    private void atenderSiguienteSolicitud() {
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
                    && esCompatible(u.getTipo(), solicitud.getTipoServicio()) && u.getZona() == solicitud.getZona()) {
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
            return;
        }

        ListaEnlazadaSimple.Iterador<Tecnico> itTecnicos = iteradorTecnicos();
        Tecnico tecnicoDisponible = null;

        while (itTecnicos.tieneSiguiente()) {
            Tecnico t = itTecnicos.siguiente();
            if (t.isDisponibilidad() && t.getEstado() != EstadoTecnico.ASIGNADO && t.getEspecialidad() == solicitud.getTipoServicio()) {
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
            return;
        }

        solicitud.setUnidadAsignada(unidadDisponible);
        solicitud.setTecnico(tecnicoDisponible);
        unidadDisponible.setDisponibilidad(false);
        unidadDisponible.setEstado(EstadoUnidad.OCUPADO);
        tecnicoDisponible.setDisponibilidad(false);
        tecnicoDisponible.setEstado(EstadoTecnico.ASIGNADO);

        solicitud.setEstado(EstadoSolicitud.EN_EJECUCION);

        historial.apilar(new OperacionCambioEstadoUnidad(unidadDisponible, EstadoUnidad.ACTIVO));
        historial.apilar(new OperacionAsignacionTecnico(tecnicoDisponible, solicitud));
    }

    public void cerrarSolicitud(SolicitudServicio solicitud) {
        solicitud.setEstado(EstadoSolicitud.CERRADA);

        if (solicitud.getUnidadAsignada() != null) {
            solicitud.getUnidadAsignada().setDisponibilidad(true);
            solicitud.getUnidadAsignada().setEstado(EstadoUnidad.ACTIVO);
        }

        if (solicitud.getTecnico() != null) {
            solicitud.getTecnico().setDisponibilidad(true);
            solicitud.getTecnico().setEstado(EstadoTecnico.DISPONIBLE);
        }

        listaCerradas.agregar(solicitud);
        historial.apilar(new OperacionCierreServicio(solicitud));
    }

    public Kit añadirKit(String descripcion, boolean completo, boolean requiereReposicion) {
        Kit kit = new Kit(descripcion, completo, requiereReposicion);
        listaKits.agregar(kit);
        return kit;
    }

    public void ingresarKitARevision(Kit kit) {
        pilaKits.apilar(kit);
    }

    public void retirarKitDeRevision() {
        pilaKits.desapilar();
    }
    
    public void eliminarKit(Kit kit){
        listaKits.eliminar(kit);
    }

    public void actualizarKit(Kit kit, String id, String descripcion, boolean completo, boolean requiereReposicion) {
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

    public void registrarOperacion(Operacion operacion) {
        historial.apilar(operacion);
    }

    public void deshacerOperacion() {
        Operacion operacion = historial.verCima();
        historial.desapilar();
        operacion.deshacer();
    }

    public Operacion verUltimaOperacion() {
        return historial.verCima();
    }

    public ListaEnlazadaSimple.Iterador<SolicitudServicio> iteradorCerradas() {
        return listaCerradas.iterador();
    }

    public void csv() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("reporte.csv"));
        writer.write("id,   cliente,    tipoServicio,    zona");

        ListaEnlazadaSimple.Iterador<SolicitudServicio> it = iteradorCerradas();

        writer.newLine();
        while (it.tieneSiguiente()) {
            SolicitudServicio s = it.siguiente();
            writer.write(s.getId() + ", " + s.getCliente() + ", " + s.getTipoServicio() + ", " + s.getZona());
            writer.newLine();
        }
        writer.close();
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
        UnidadServicio unidadEncontrada = listaUnidades.obtenerPorUuid(uuidBuscado);
        return unidadEncontrada;
    }

    public Kit buscarKitPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null) {
            return null;
        }

        Kit kitEncontrado = listaKits.obtenerPorUuid(uuidBuscado);

        return kitEncontrado;
    }

    public Tecnico buscarTecnicoPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null) {
            return null;
        }
        Tecnico tecnicoEncontrado = listaTecnicos.obtenerPorUuid(uuidBuscado);
        return tecnicoEncontrado;
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

}
