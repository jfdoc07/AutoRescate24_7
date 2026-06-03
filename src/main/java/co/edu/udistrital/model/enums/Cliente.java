package co.edu.udistrital.model.enums;

/**
 * Representa los tipos de clientes que pueden solicitar servicios
 * dentro del sistema AutoRescate 24/7.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public enum Cliente {

    /**
     * Cliente particular que solicita servicios de manera individual.
     */
    PARTICULAR,

    /**
     * Empresa que contrata servicios para sus vehículos o personal.
     */
    EMPRESA,

    /**
     * Compañía aseguradora que solicita servicios para sus afiliados.
     */
    ASEGURADORA
}
