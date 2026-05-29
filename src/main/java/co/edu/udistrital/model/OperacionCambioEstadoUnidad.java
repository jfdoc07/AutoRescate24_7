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

    public OperacionCambioEstadoUnidad(UnidadServicio unidad, EstadoUnidad anterior) {
        this.unidad = unidad;
        this.anterior = anterior;
    }

    @Override
    public void deshacer() {
        unidad.setEstado(anterior);
        
    }
    
    
}
