/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.EstadoUnidad;

/**
 *
 * @author Usuario
 */
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
        return "OperacionCambioEstadoUnidad{" + "unidad=" + unidad + ", anterior=" + anterior + '}';
    }
    
    
}
