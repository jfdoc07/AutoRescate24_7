package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.TipoCliente;

/**
 *
 * @author Usuario
 */
public class Cliente {
    private String id;
    private String nombre;
    private TipoCliente tipo;

    public Cliente(String id, String nombre, TipoCliente tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo.toString();
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + '}';
    }
    
    
}
