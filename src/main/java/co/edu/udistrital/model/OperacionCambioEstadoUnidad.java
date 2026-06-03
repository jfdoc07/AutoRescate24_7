package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.EstadoUnidad;

public class OperacionCambioEstadoUnidad  extends Operacion{
    private UnidadServicio unidad;
    private EstadoUnidad anterior;
    private boolean disponibilidadAnterior;

    public OperacionCambioEstadoUnidad(UnidadServicio unidad, EstadoUnidad anterior, boolean disponibilidadAnterior) {
        this.unidad = unidad;
        this.anterior = anterior;
        this.disponibilidadAnterior = disponibilidadAnterior;
    }

    @Override
    public void deshacer(Sistema sistema) {
        unidad.setEstado(anterior);
        unidad.setDisponibilidad(disponibilidadAnterior);
    }

    @Override
    public String toString() {
        return "Unidad " + unidad.getTipo() + "cambiada de " + anterior.toString() + '}';
    }
    
    
}
