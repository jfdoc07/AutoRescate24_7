package co.edu.udistrital.model.enums;

/**
 * Representa los posibles estados operativos de una unidad de servicio
 * dentro del sistema AutoRescate 24/7.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public enum EstadoUnidad {

    /**
     * La unidad se encuentra operativa y disponible para ser asignada.
     */
    ACTIVO,

    /**
     * La unidad está atendiendo una solicitud de servicio.
     */
    OCUPADO,

    /**
     * La unidad se encuentra fuera de servicio por mantenimiento.
     */
    MANTENIMIENTO
}
