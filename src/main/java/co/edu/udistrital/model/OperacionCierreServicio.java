package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.*;

/**
 * Representa una operación de cierre de servicio.
 * Permite revertir el cierre de una solicitud restaurando los recursos
 * asociados y regresando la solicitud al estado de ejecución.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class OperacionCierreServicio extends Operacion {

    /**
     * Solicitud de servicio cerrada.
     */
    private SolicitudServicio solicitud;

    /**
     * Unidad de servicio asociada a la solicitud.
     */
    private UnidadServicio unidadServicio;

    /**
     * Técnico asignado a la solicitud.
     */
    private Tecnico tecnico;

    /**
     * Kit utilizado durante la atención del servicio.
     */
    private Kit kit;

    /**
     * Construye una operación de cierre de servicio a partir de una solicitud.
     *
     * @param solicitud Solicitud de servicio que fue cerrada.
     */
    public OperacionCierreServicio(SolicitudServicio solicitud) {
        this.solicitud = solicitud;
        unidadServicio = solicitud.getUnidadAsignada();
        tecnico = solicitud.getTecnico();
        kit = solicitud.getKitAsignado();
    }

    /**
     * Revierte el cierre de una solicitud de servicio.
     * Restablece la ocupación de los recursos asociados, elimina el kit de la
     * lista general y devuelve la solicitud al estado de ejecución.
     *
     * @param sistema Sistema sobre el cual se realizará la reversión.
     */
    @Override
    public void deshacer(Sistema sistema) {
        System.out.println("-> Deshaciendo Cierre de Servicio: Reabriendo solicitud...");

        if (tecnico != null) {
            tecnico.setEstado(EstadoTecnico.ASIGNADO);
            tecnico.setDisponibilidad(false);
        }

        if (unidadServicio != null) {
            unidadServicio.setEstado(EstadoUnidad.OCUPADO);
            unidadServicio.setDisponibilidad(false);
        }

        if (kit != null) {
            sistema.getListaKits().eliminar(kit);
        }

        solicitud.setEstado(EstadoSolicitud.EN_EJECUCION);

        sistema.getListaCerradas().eliminar(solicitud);
        sistema.getListaEnEjecucion().agregar(solicitud);

        this.solicitud.setEstado(EstadoSolicitud.EN_EJECUCION);
    }

    /**
     * Obtiene una representación textual de la operación.
     *
     * @return Descripción de la operación de cierre de servicio.
     */
    @Override
    public String toString() {
        return "Operación cerrada " + solicitud.getDescripcion();
    }
}
