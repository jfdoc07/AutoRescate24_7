package co.edu.udistrital.model;

/**
 * Define la estructura base para las operaciones que pueden realizarse
 * dentro del sistema. Las clases que hereden de esta deberán implementar
 * la lógica necesaria para deshacer una operación específica.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public abstract class Operacion {

    /**
     * Revierte los efectos de la operación sobre el sistema.
     *
     * @param sistema Instancia del sistema sobre la cual se realizará la acción de deshacer.
     */
    public abstract void deshacer(Sistema sistema);

    /**
     * Obtiene una representación textual de la operación.
     *
     * @return Cadena que describe la operación.
     */
    @Override
    public abstract String toString();
}
