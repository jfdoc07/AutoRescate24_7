package co.edu.udistrital.model.enums;

/**
 * Representa los estados posibles de un técnico dentro del sistema.
 * Permite determinar si un técnico puede ser asignado a una solicitud
 * de servicio o si ya se encuentra atendiendo una.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public enum EstadoTecnico {

    /**
     * El técnico está disponible para recibir asignaciones.
     */
    DISPONIBLE,

    /**
     * El técnico se encuentra asignado a una solicitud de servicio.
     */
    ASIGNADO
}
