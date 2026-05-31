
package co.edu.udistrital.model;

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Usuario
 */
public class Kit implements Identificable{
    private String id;
    private String descripcion;
    private boolean completo;
    private boolean requiereReposicion;

    public Kit( String descripcion, boolean completo, boolean requiereReposicion) {
        Random rand = new Random();
        this.id = UUID.randomUUID().toString();
        this.descripcion = descripcion;
        this.completo = completo;
        this.requiereReposicion = requiereReposicion;
    }
    
    @Override
    public String getUuid() {
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
