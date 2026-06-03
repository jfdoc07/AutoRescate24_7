package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.*;

/**
 * Representa una operación de atención de servicio dentro del sistema.
 * Esta operación permite revertir la asignación de recursos realizada
 * durante la puesta en servicio de una solicitud.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class OperacionAtencionServicio extends Operacion {

    /**
     * Solicitud de servicio asociada a la operación.
     */
    private SolicitudServicio solicitud;

    /**
     * Unidad de servicio asignada a la solicitud.
     */
    private UnidadServicio unidadServicio;

    /**
     * Técnico asignado para atender la solicitud.
     */
    private Tecnico tecnico;

    /**
     * Kit utilizado durante la atención del servicio.
     */
    private Kit kit;

    /**
     * Construye una operación de atención de servicio con los recursos involucrados.
     *
     * @param solicitud Solicitud de servicio atendida.
     * @param unidadServicio Unidad de servicio asignada.
     * @param tecnico Técnico responsable de la atención.
     * @param kit Kit utilizado en la operación.
     */
    public OperacionAtencionServicio(SolicitudServicio solicitud, UnidadServicio unidadServicio, Tecnico tecnico, Kit kit) {
        this.solicitud = solicitud;
        this.unidadServicio = unidadServicio;
        this.tecnico = tecnico;
        this.kit = kit;
    }

    /**
     * Revierte completamente la atención de una solicitud de servicio.
     * Restablece la disponibilidad de los recursos utilizados, devuelve
     * el kit al inventario y retorna la solicitud a su cola correspondiente.
     *
     * @param sistema Sistema sobre el cual se ejecutará la reversión.
     */
    @Override
    public void deshacer(Sistema sistema) {
        System.out.println("-> Ejecutando Rollback total de recursos implicados...");

        if (tecnico != null) {
            tecnico.setEstado(EstadoTecnico.DISPONIBLE);
            tecnico.setDisponibilidad(true);
        }

        if (unidadServicio != null) {
            unidadServicio.setEstado(EstadoUnidad.ACTIVO);
            unidadServicio.setDisponibilidad(true);
        }

        if (solicitud.getKitAsignado() != null) {
            Kit kitUsado = solicitud.getKitAsignado();
            kitUsado.setCompleto(true);
            kitUsado.setRequiereReposicion(false);

            sistema.getPilaKits().apilar(kitUsado);
            solicitud.setKitAsignado(null);
        }

        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        sistema.getListaEnEjecucion().eliminar(solicitud);

        if (solicitud.getCriticidad() == NivelCriticidad.CRITICA) {
            sistema.getColaCritica().insertar(solicitud);
        } else {
            sistema.getColaOrdinaria().enqueue(solicitud);
        }
    }

    /**
     * Obtiene una descripción textual de la operación.
     *
     * @return Descripción de la operación realizada.
     */
    @Override
    public String toString() {
        return "Operación puesta en servicio " + solicitud.getDescripcion();
    }
}
