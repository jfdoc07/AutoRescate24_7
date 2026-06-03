package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.EstadoUnidad;

/**
 * Representa una operación de cambio de estado realizada sobre una unidad
 * de servicio. Permite restaurar el estado y la disponibilidad anteriores
 * mediante el mecanismo de deshacer.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class OperacionCambioEstadoUnidad extends Operacion {

    /**
     * Unidad de servicio afectada por la operación.
     */
    private UnidadServicio unidad;

    /**
     * Estado que tenía la unidad antes del cambio.
     */
    private EstadoUnidad anterior;

    /**
     * Disponibilidad que tenía la unidad antes del cambio.
     */
    private boolean disponibilidadAnterior;

    /**
     * Construye una operación de cambio de estado para una unidad de servicio.
     *
     * @param unidad Unidad afectada.
     * @param anterior Estado previo de la unidad.
     * @param disponibilidadAnterior Disponibilidad previa de la unidad.
     */
    public OperacionCambioEstadoUnidad(UnidadServicio unidad, EstadoUnidad anterior, boolean disponibilidadAnterior) {
        this.unidad = unidad;
        this.anterior = anterior;
        this.disponibilidadAnterior = disponibilidadAnterior;
    }

    /**
     * Restaura el estado y la disponibilidad que tenía la unidad antes
     * de ejecutarse la operación.
     *
     * @param sistema Sistema sobre el cual se realiza la reversión.
     */
    @Override
    public void deshacer(Sistema sistema) {
        unidad.setEstado(anterior);
        unidad.setDisponibilidad(disponibilidadAnterior);
    }

    /**
     * Obtiene una representación textual de la operación.
     *
     * @return Descripción de la operación de cambio de estado.
     */
    @Override
    public String toString() {
        return "OperacionCambioEstadoUnidad{" + "unidad=" + unidad + ", anterior=" + anterior + '}';
    }
}
