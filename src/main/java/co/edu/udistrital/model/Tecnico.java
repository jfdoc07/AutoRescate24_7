/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;
import co.edu.udistrital.model.enums.EstadoTecnico;
import co.edu.udistrital.model.enums.TipoServicio;
import java.util.UUID;

public class Tecnico implements Identificable{
    private UUID id;
    private String nombre;
    private TipoServicio especialidad;
    private boolean disponibilidad;
    private EstadoTecnico estado;

    public Tecnico(String nombre, TipoServicio especialidad, boolean disponibilidad, EstadoTecnico estado) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.disponibilidad = disponibilidad;
        this.estado = estado;
    }

    
    
    @Override
    public UUID getUuid() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoServicio getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(TipoServicio especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public EstadoTecnico getEstado() {
        return estado;
    }

    public void setEstado(EstadoTecnico estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
    
}
