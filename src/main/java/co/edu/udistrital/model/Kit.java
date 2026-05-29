
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

    public Kit(String id, String descripcion, boolean completo, boolean requiereReposicion) {
        this.id = id;
        this.descripcion = descripcion;
        this.completo = completo;
        this.requiereReposicion = requiereReposicion;
    }

    
    
    
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
