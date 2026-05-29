/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;

/**
 *
 * @author Usuario
 */
public class Kit {
    private String id;
    private String descripcion;
    private boolean completo;
    private boolean requiereReposicion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    public boolean isRequiereReposicion() {
        return requiereReposicion;
    }

    public void setRequiereReposicion(boolean requiereReposicion) {
        this.requiereReposicion = requiereReposicion;
    }
    
    
}
