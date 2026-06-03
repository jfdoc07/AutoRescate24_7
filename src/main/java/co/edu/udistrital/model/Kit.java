package co.edu.udistrital.model;

import java.util.UUID;

public class Kit implements Identificable{
    private UUID id;
    private String descripcion;
    private boolean completo;
    private boolean requiereReposicion;

    public Kit(String descripcion, boolean completo, boolean requiereReposicion) {
        this.id = UUID.randomUUID();
        this.descripcion = descripcion;
        this.completo = completo;
        this.requiereReposicion = requiereReposicion;
    }
    
    @Override
    public UUID getUuid() {
        return id;
    }

    public void setId(UUID id) {
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
