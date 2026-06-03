package co.edu.udistrital.model;

import java.util.UUID;

/**
 * Define el comportamiento que deben implementar las clases cuyos objetos
 * poseen un identificador único universal (UUID).
 * Permite acceder al identificador de manera uniforme dentro del sistema.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public interface Identificable {

    /**
     * Obtiene el identificador único asociado al objeto.
     *
     * @return UUID que identifica de forma única al objeto.
     */
    UUID getUuid();
}
