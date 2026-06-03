package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.Cliente;
import co.edu.udistrital.model.enums.TipoServicio;
import co.edu.udistrital.model.enums.NivelCriticidad;
import co.edu.udistrital.model.enums.EstadoSolicitud;
import co.edu.udistrital.model.enums.Zona;
import java.util.UUID;

/**
 * Representa una solicitud de servicio registrada en el sistema.
 * Contiene la información del cliente, el tipo de servicio solicitado,
 * los recursos asignados y el estado actual de la solicitud.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class SolicitudServicio implements Identificable {

    /**
     * Identificador único de la solicitud.
     */
    private UUID id;

    /**
     * Tipo de cliente que realiza la solicitud.
     */
    private Cliente cliente;

    /**
     * Tipo de servicio solicitado.
     */
    private TipoServicio tipoServicio;

    /**
     * Descripción de la solicitud.
     */
    private String descripcion;

    /**
     * Nivel de criticidad de la solicitud.
     */
    private NivelCriticidad criticidad;

    /**
     * Unidad de servicio asignada.
     */
    private UnidadServicio unidadAsignada;

    /**
     * Técnico asignado a la solicitud.
     */
    private Tecnico tecnico;

    /**
     * Kit asignado para la atención del servicio.
     */
    private Kit kitAsignado;

    /**
     * Estado actual de la solicitud.
     */
    private EstadoSolicitud estado;

    /**
     * Zona en la que se realizará el servicio.
     */
    private Zona zona;

    /**
     * Puntaje utilizado para determinar la criticidad de la solicitud.
     */
    private int puntajeCriticidad;

    /**
     * Construye una nueva solicitud de servicio.
     *
     * @param cliente Cliente que realiza la solicitud.
     * @param tipoServicio Tipo de servicio requerido.
     * @param descripcion Descripción de la solicitud.
     * @param criticidad Nivel de criticidad inicial.
     * @param unidadAsignada Unidad asignada a la solicitud.
     * @param tecnico Técnico asignado.
     * @param kitAsignado Kit asignado.
     * @param estado Estado inicial de la solicitud.
     * @param zona Zona donde se prestará el servicio.
     */
    public SolicitudServicio(Cliente cliente, TipoServicio tipoServicio, String descripcion,
            NivelCriticidad criticidad, UnidadServicio unidadAsignada,
            Tecnico tecnico, Kit kitAsignado, EstadoSolicitud estado, Zona zona) {

        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.tipoServicio = tipoServicio;
        this.descripcion = descripcion;
        this.criticidad = criticidad;
        this.unidadAsignada = unidadAsignada;
        this.tecnico = tecnico;
        this.kitAsignado = kitAsignado;
        this.estado = estado;
        this.zona = zona;
    }

    /**
     * Obtiene el puntaje de criticidad calculado para la solicitud.
     *
     * @return Puntaje de criticidad.
     */
    public int getPuntajeCriticidad() {
        return puntajeCriticidad;
    }

    /**
     * Establece el puntaje de criticidad de la solicitud.
     *
     * @param puntajeCriticidad Nuevo puntaje de criticidad.
     */
    public void setPuntajeCriticidad(int puntajeCriticidad) {
        this.puntajeCriticidad = puntajeCriticidad;
    }

    /**
     * Modifica el nivel de criticidad de la solicitud.
     *
     * @param criticidad Nuevo nivel de criticidad.
     */
    public void setCriticidad(NivelCriticidad criticidad) {
        this.criticidad = criticidad;
    }

    /**
     * Calcula el puntaje de criticidad según el tipo de servicio
     * y el tipo de cliente.
     *
     * @return Puntaje total de criticidad.
     */
    public int calcularPuntaje() {
        int puntaje = 0;

        switch (tipoServicio) {
            case GRUA:
                puntaje += 30;
                break;
            case REVISION_MECANICA:
                puntaje += 20;
                break;
            case PASO_CORRIENTE:
                puntaje += 15;
                break;
            case CAMBIO_LLANTA:
                puntaje += 10;
                break;
            case APERTURA_PUERTAS:
                puntaje += 10;
                break;
            case COMBUSTIBLE:
                puntaje += 5;
                break;
        }

        switch (cliente) {
            case ASEGURADORA:
                puntaje += 30;
                break;
            case EMPRESA:
                puntaje += 20;
                break;
            case PARTICULAR:
                puntaje += 5;
                break;
        }

        return puntaje;
    }

    /**
     * Determina el nivel de criticidad de la solicitud a partir
     * del puntaje calculado.
     *
     * @return Nivel de criticidad correspondiente.
     */
    public NivelCriticidad calcularNivel() {
        return puntajeCriticidad >= 35
                ? NivelCriticidad.CRITICA
                : NivelCriticidad.ORDINARIA;
    }

    /**
     * Obtiene la prioridad de la solicitud.
     *
     * @return Puntaje de criticidad utilizado como prioridad.
     */
    public int getPrioridad() {
        return puntajeCriticidad;
    }

    /**
     * Obtiene el identificador único de la solicitud.
     *
     * @return UUID de la solicitud.
     */
    @Override
    public UUID getUuid() {
        return this.id;
    }

    /**
     * Obtiene el cliente asociado a la solicitud.
     *
     * @return Cliente de la solicitud.
     */
    public Cliente getCliente() {
        return this.cliente;
    }

    /**
     * Modifica el cliente asociado a la solicitud.
     *
     * @param cliente Nuevo cliente.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el tipo de servicio solicitado.
     *
     * @return Tipo de servicio.
     */
    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    /**
     * Modifica el tipo de servicio solicitado.
     *
     * @param tipoServicio Nuevo tipo de servicio.
     */
    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    /**
     * Obtiene la descripción de la solicitud.
     *
     * @return Descripción registrada.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripción de la solicitud.
     *
     * @param descripcion Nueva descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el nivel de criticidad de la solicitud.
     *
     * @return Nivel de criticidad.
     */
    public NivelCriticidad getCriticidad() {
        return criticidad;
    }

    /**
     * Obtiene la unidad asignada a la solicitud.
     *
     * @return Unidad de servicio asignada.
     */
    public UnidadServicio getUnidadAsignada() {
        return unidadAsignada;
    }

    /**
     * Asigna una unidad de servicio a la solicitud.
     *
     * @param unidadAsignada Unidad que atenderá la solicitud.
     */
    public void setUnidadAsignada(UnidadServicio unidadAsignada) {
        this.unidadAsignada = unidadAsignada;
    }

    /**
     * Obtiene el técnico asignado a la solicitud.
     *
     * @return Técnico responsable.
     */
    public Tecnico getTecnico() {
        return tecnico;
    }

    /**
     * Asigna un técnico a la solicitud.
     *
     * @param tecnico Técnico responsable.
     */
    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    /**
     * Elimina la referencia al técnico asignado si coincide con el indicado.
     *
     * @param t Técnico que se desea eliminar.
     */
    public void eliminarTecnico(Tecnico t) {
        if (tecnico != null && t.equals(this.tecnico)) {
            tecnico = null;
        }
    }

    /**
     * Obtiene el estado actual de la solicitud.
     *
     * @return Estado de la solicitud.
     */
    public EstadoSolicitud getEstado() {
        return estado;
    }

    /**
     * Modifica el estado de la solicitud.
     *
     * @param estado Nuevo estado.
     */
    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la zona asociada a la solicitud.
     *
     * @return Zona de atención.
     */
    public Zona getZona() {
        return zona;
    }

    /**
     * Modifica la zona asociada a la solicitud.
     *
     * @param zona Nueva zona.
     */
    public void setZona(Zona zona) {
        this.zona = zona;
    }

    /**
     * Obtiene el kit asignado a la solicitud.
     *
     * @return Kit asignado.
     */
    public Kit getKitAsignado() {
        return kitAsignado;
    }

    /**
     * Asigna un kit a la solicitud.
     *
     * @param kitAsignado Kit que será utilizado.
     */
    public void setKitAsignado(Kit kitAsignado) {
        this.kitAsignado = kitAsignado;
    }
}
