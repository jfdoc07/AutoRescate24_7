package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * Clase central que administra toda la lógica de negocio del sistema
 * AutoRescate 24/7. Gestiona las solicitudes de servicio, unidades,
 * técnicos, kits de herramientas, historial de operaciones y reportes.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class Sistema {

    /**
     * Cola que almacena las solicitudes ordinarias pendientes.
     */
    private Cola<SolicitudServicio> colaOrdinaria;

    /**
     * Cola de prioridad utilizada para almacenar solicitudes críticas.
     */
    private ColaPrioridad colaCritica;

    /**
     * Historial de operaciones realizadas en el sistema.
     */
    private Pila<Operacion> historial;

    /**
     * Pila de kits disponibles para asignación.
     */
    private Pila<Kit> pilaKits;

    /**
     * Lista de unidades de servicio registradas.
     */
    private ListaEnlazadaSimple<UnidadServicio> listaUnidades;

    /**
     * Lista de técnicos registrados.
     */
    private ListaEnlazadaSimple<Tecnico> listaTecnicos;

    /**
     * Lista de kits registrados en el sistema.
     */
    private ListaEnlazadaSimple<Kit> listaKits;

    /**
     * Lista de clientes registrados.
     */
    private ListaEnlazadaSimple<Cliente> listaClientes;

    /**
     * Lista de solicitudes que actualmente se encuentran en ejecución.
     */
    private ListaEnlazadaSimple<SolicitudServicio> listaEnEjecucion;

    /**
     * Lista histórica de solicitudes cerradas.
     */
    private ListaEnlazadaSimple<SolicitudServicio> listaCerradas;

    /**
     * Construye una nueva instancia del sistema inicializando todas las
     * estructuras de datos necesarias para su funcionamiento.
     */
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

    /**
     * Registra una nueva unidad de servicio en el sistema.
     *
     * @param tipo Tipo de unidad a registrar.
     * @param zona Zona de operación de la unidad.
     */
    public void añadirUnidad(TipoUnidad tipo, Zona zona) {
        UnidadServicio unidad = new UnidadServicio(tipo, EstadoUnidad.ACTIVO, zona, true);
        listaUnidades.agregar(unidad);
    }

    /**
     * Obtiene un iterador para recorrer las unidades registradas.
     *
     * @return Iterador de unidades.
     */
    public ListaEnlazadaSimple.Iterador<UnidadServicio> iteradorUnidades() {
        return listaUnidades.iterador();
    }

    /**
     * Actualiza la información de una unidad registrada.
     *
     * @param unidad Unidad a actualizar.
     * @param tipo Nuevo tipo de unidad.
     * @param estado Nuevo estado.
     * @param zona Nueva zona.
     * @param disponibilidad Nueva disponibilidad.
     */
    public void actualizarUnidad(UnidadServicio unidad, TipoUnidad tipo,
            EstadoUnidad estado, Zona zona, boolean disponibilidad) {

        if (listaUnidades.contiene(unidad)) {
            unidad.setTipo(tipo);
            unidad.setEstado(estado);
            unidad.setZona(zona);
            unidad.setDisponibilidad(disponibilidad);
        }
    }

    /**
     * Elimina una unidad del sistema.
     *
     * @param unidad Unidad a eliminar.
     */
    public void eliminarUnidad(UnidadServicio unidad) {
        listaUnidades.eliminar(unidad);
    }

    /**
     * Envía una unidad a mantenimiento o la devuelve a operación activa.
     * Además registra la operación en el historial para permitir deshacerla.
     *
     * @param unidadID Identificador de la unidad.
     * @return Mensaje indicando el resultado de la operación.
     * @throws IllegalArgumentException Si la unidad no existe o está ocupada.
     */
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
                    return "Unidad sacada de mantenimiento";
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
                    throw new IllegalArgumentException("La unidad se encuentra ocupada, no se puede enviar a mantenimiento.");
            }
        }
    }

    /**
     * Registra un nuevo técnico en el sistema.
     *
     * @param nombre Nombre del técnico.
     * @param especialidad Especialidad del técnico.
     * @param disponibilidad Disponibilidad inicial.
     * @param estado Estado inicial.
     */
    public void añadirTecnico(String nombre, TipoServicio especialidad,
            boolean disponibilidad, EstadoTecnico estado) {

        Tecnico tecnico = new Tecnico(nombre, especialidad,
                disponibilidad, estado);

        listaTecnicos.agregar(tecnico);

        System.out.println("Tecnico registrado con exito "
                + tecnico.getNombre() + " "
                + tecnico.getEspecialidad() + " "
                + tecnico.getEstado());
    }

    /**
     * Actualiza la información de un técnico registrado.
     *
     * @param tecnico Técnico a actualizar.
     * @param id Identificador recibido desde la interfaz.
     * @param nombre Nuevo nombre.
     * @param especialidad Nueva especialidad.
     * @param disponibilidad Nueva disponibilidad.
     * @param estado Nuevo estado.
     */
    public void actualizarTecnico(Tecnico tecnico, String id,
            String nombre, TipoServicio especialidad,
            boolean disponibilidad, EstadoTecnico estado) {

        if (listaTecnicos.contiene(tecnico)) {
            tecnico.setNombre(nombre);
            tecnico.setEspecialidad(especialidad);
            tecnico.setDisponibilidad(disponibilidad);
            tecnico.setEstado(estado);
        }
    }

    /**
     * Elimina un técnico del sistema.
     *
     * @param tecnico Técnico a eliminar.
     */
    public void eliminarTecnico(Tecnico tecnico) {
        listaTecnicos.eliminar(tecnico);
    }

    /**
     * Obtiene un iterador para recorrer los técnicos registrados.
     *
     * @return Iterador de técnicos.
     */
    public ListaEnlazadaSimple.Iterador<Tecnico> iteradorTecnicos() {
        return listaTecnicos.iterador();
    }

    /**
     * Registra una nueva solicitud de servicio y la ubica en la estructura
     * correspondiente según su nivel de criticidad.
     *
     * @param cliente Tipo de cliente que realiza la solicitud.
     * @param tipoServicio Servicio solicitado.
     * @param descripcion Descripción de la solicitud.
     * @param zona Zona donde se requiere el servicio.
     */
    public void registrarSolicitud(Cliente cliente,
            TipoServicio tipoServicio,
            String descripcion,
            Zona zona) {
        SolicitudServicio solicitud = new SolicitudServicio(cliente, tipoServicio, descripcion, NivelCriticidad.ORDINARIA, null, null, null, EstadoSolicitud.PENDIENTE, zona);
        solicitud.setPuntajeCriticidad(solicitud.calcularPuntaje());
        solicitud.setCriticidad(solicitud.calcularNivel());

        if (solicitud.getCriticidad() == NivelCriticidad.CRITICA) {
            colaCritica.insertar(solicitud);
        } else {
            colaOrdinaria.enqueue(solicitud);
        }
    }

    /**
     * Atiende la siguiente solicitud disponible asignando automáticamente
     * una unidad, un técnico y un kit compatibles.
     *
     * @throws IllegalArgumentException Si no existen recursos disponibles.
     */
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

    /**
     * Finaliza una solicitud en ejecución liberando los recursos asignados,
     * almacenándola en el historial de solicitudes cerradas y generando
     * el reporte actualizado.
     *
     * @param solicitudIdStr Identificador de la solicitud.
     * @return Mensaje indicando el resultado de la operación.
     * @throws IllegalArgumentException Si la solicitud no existe.
     */
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

    /**
     * Registra un nuevo kit de herramientas.
     *
     * @param descripcion Descripción del kit.
     * @param completo Indica si el kit está completo.
     * @param requiereReposicion Indica si requiere reposición.
     * @return Kit creado.
     */
    public Kit añadirKit(String descripcion,
            boolean completo,
            boolean requiereReposicion) {
        Kit kit = new Kit(descripcion, completo, requiereReposicion);
        listaKits.agregar(kit);
        return kit;
    }

    /**
     * Envía un kit a la pila de kits disponibles.
     *
     * @param idKit Identificador del kit.
     * @return Mensaje de confirmación.
     */
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
        return "Kit apilado con éxito.";
    }

    /**
     * Obtiene un kit disponible desde la pila.
     *
     * @return Kit extraído.
     */
    public Kit utilizarKit() {
        return pilaKits.desapilar();
    }

    /**
     * Elimina un kit del sistema.
     *
     * @param kit Kit a eliminar.
     */
    public void eliminarKit(Kit kit) {
        listaKits.eliminar(kit);
    }

    /**
     * Actualiza la información de un kit.
     *
     * @param kit Kit a modificar.
     * @param id Nuevo identificador.
     * @param descripcion Nueva descripción.
     * @param completo Nuevo estado de completitud.
     * @param requiereReposicion Nuevo estado de reposición.
     */
    public void actualizarKit(Kit kit,
            UUID id,
            String descripcion,
            boolean completo,
            boolean requiereReposicion) {
        if (pilaKits.contiene(kit)) {
            kit.setId(id);
            kit.setDescripcion(descripcion);
            kit.setCompleto(completo);
            kit.setRequiereReposicion(requiereReposicion);
        }
    }

    /**
     * Obtiene el kit ubicado en la cima de la pila.
     *
     * @return Kit en la cima.
     */
    public Kit verCimaKits() {
        return pilaKits.verCima();
    }

    /**
     * Obtiene la lista de kits registrados en el sistema.
     *
     * @return Lista de kits.
     */
    public ListaEnlazadaSimple<Kit> getListaKits() {
        return listaKits;
    }

    /**
     * Registra una operación en el historial.
     *
     * @param operacion Operación realizada.
     */
    public void registrarOperacion(Operacion operacion) {
        historial.apilar(operacion);
    }

    /**
     * Deshace la última operación registrada.
     *
     * @return Mensaje de confirmación.
     * @throws IllegalArgumentException Si el historial está vacío.
     */
    public String deshacerOperacion() {
        if (historial == null || historial.estaVacia()) throw new IllegalArgumentException("El historial está vacío.");
        Operacion operacion = historial.verCima();
        historial.desapilar();
        operacion.deshacer(this);
        return "Ultima operación revertida.";
    }

    /**
     * Obtiene la última operación registrada.
     *
     * @return Última operación del historial.
     */
    public Operacion verUltimaOperacion() {
        return historial.verCima();
    }

    /**
     * Obtiene un iterador para recorrer las solicitudes cerradas.
     *
     * @return Iterador de solicitudes cerradas.
     */
    public ListaEnlazadaSimple.Iterador<SolicitudServicio> iteradorCerradas() {
        return listaCerradas.iterador();
    }

    /**
     * Genera un archivo CSV con las solicitudes cerradas registradas.
     *
     * @return Mensaje indicando el resultado de la operación.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
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

    /**
     * Determina si una unidad es compatible con un tipo de servicio.
     *
     * @param tipoUnidad Tipo de unidad.
     * @param tipoServicio Tipo de servicio.
     * @return true si es compatible, false en caso contrario.
     */
    private boolean esCompatible(TipoUnidad tipoUnidad,
            TipoServicio tipoServicio) {
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

    /**
     * Busca una unidad por su identificador único.
     *
     * @param uuidBuscado UUID de la unidad.
     * @return Unidad encontrada o null si no existe.
     */
    public UnidadServicio buscarUnidadPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null) {
            return null;
        }
        return listaUnidades.obtenerPorUuid(uuidBuscado);
    }

    /**
     * Busca un kit por su identificador único.
     *
     * @param uuidBuscado UUID del kit.
     * @return Kit encontrado o null si no existe.
     */
    public Kit buscarKitPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null) {
            return null;
        }
        return listaKits.obtenerPorUuid(uuidBuscado);
    }

    /**
     * Busca un técnico por su identificador único.
     *
     * @param uuidBuscado UUID del técnico.
     * @return Técnico encontrado o null si no existe.
     */
    public Tecnico buscarTecnicoPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null) {
            return null;
        }
        return listaTecnicos.obtenerPorUuid(uuidBuscado);
    }

    /**
     * Obtiene la cola de solicitudes ordinarias pendientes de atención.
     *
     * @return Cola de solicitudes ordinarias.
     */
    public Cola<SolicitudServicio> getColaOrdinaria() {
        return colaOrdinaria;
    }

    /**
     * Obtiene la cola de prioridad que almacena las solicitudes críticas.
     *
     * @return Cola de solicitudes críticas.
     */
    public ColaPrioridad getColaCritica() {
        return colaCritica;
    }

    /**
     * Obtiene el historial de operaciones realizadas en el sistema.
     *
     * @return Pila que contiene el historial de operaciones.
     */
    public Pila<Operacion> getHistorial() {
        return historial;
    }

    /**
     * Obtiene la pila de kits disponibles para ser asignados a solicitudes.
     *
     * @return Pila de kits disponibles.
     */
    public Pila<Kit> getPilaKits() {
        return pilaKits;
    }

    /**
     * Obtiene la lista de unidades de servicio registradas en el sistema.
     *
     * @return Lista de unidades de servicio.
     */
    public ListaEnlazadaSimple<UnidadServicio> getListaUnidades() {
        return listaUnidades;
    }

    /**
     * Obtiene la lista de técnicos registrados en el sistema.
     *
     * @return Lista de técnicos.
     */
    public ListaEnlazadaSimple<Tecnico> getListaTecnicos() {
        return listaTecnicos;
    }

    /**
     * Obtiene la lista de clientes registrados en el sistema.
     *
     * @return Lista de clientes.
     */
    public ListaEnlazadaSimple<Cliente> getListaClientes() {
        return listaClientes;
    }

    /**
     * Obtiene la lista de solicitudes que han sido cerradas.
     *
     * @return Lista de solicitudes cerradas.
     */
    public ListaEnlazadaSimple<SolicitudServicio> getListaCerradas() {
        return listaCerradas;
    }

    /**
     * Obtiene la lista de solicitudes que se encuentran actualmente en ejecución.
     *
     * @return Lista de solicitudes en ejecución.
     */
    public ListaEnlazadaSimple<SolicitudServicio> getListaEnEjecucion() {
        return listaEnEjecucion;
    }
}