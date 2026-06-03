package co.edu.udistrital.model.enums;

/**
 * Representa los posibles estados de una solicitud de servicio
 * dentro del sistema AutoRescate 24/7.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public enum EstadoSolicitud {

    /**
     * La solicitud ha sido registrada y está pendiente de atención.
     */
    PENDIENTE,

    /**
     * La solicitud se encuentra actualmente en proceso de atención.
     */
    EN_EJECUCION,

    /**
     * La solicitud ha sido atendida y cerrada exitosamente.
     */
    CERRADA
}
