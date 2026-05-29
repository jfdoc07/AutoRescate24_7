/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.*;

/**
 * Clase que modela la lógica del sistema.
 *
 * @author Usuario
 */
public class Sistema {

    private Cola<SolicitudServicio> colaOrdinaria;
    private ColaPrioridad colaCritica;
    private Pila<Operacion> historial;
    private Pila<Kit> pilaKit;
    private ListaEnlazadaSimple listaUnidades;

    public void ejecucion() {

    }

    public void añadirUnidad(String id, TipoUnidad tipo, EstadoUnidad estado, String zona, boolean disponibilidad) {
        UnidadServicio unidad = new UnidadServicio(id, tipo, estado, zona, disponibilidad);
        listaUnidades.agregar(unidad);
    }

    public void verUnidades() {
        while (listaUnidades.iterador().tieneSiguiente()) {
            //printea/muestra todas las unidades
        }
    }

    public void actualizarUnidad(UnidadServicio unidad, String id, TipoUnidad tipo, EstadoUnidad estado, String zona, boolean disponibilidad) {
        while (listaUnidades.iterador().tieneSiguiente()) {
            if(listaUnidades.contiene(unidad)){
                unidad.setTipo(tipo);
                unidad.setEstado(estado);
                unidad.setZona(zona);
                unidad.setDisponibilidad(disponibilidad);
            }
        }
    }
    public void eliminarUnidad(UnidadServicio unidad){
        listaUnidades.eliminar(unidad);
    }
    
    
}
