package co.edu.udistrital.model.enums;

/**
 * Representa los niveles de criticidad que puede tener una solicitud
 * de servicio dentro del sistema AutoRescate 24/7.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public enum NivelCriticidad {

    /**
     * Solicitud con prioridad estándar que puede ser atendida
     * siguiendo el flujo normal del sistema.
     */
    ORDINARIA,

    /**
     * Solicitud de alta prioridad que requiere atención preferencial.
     */
    CRITICA
}
