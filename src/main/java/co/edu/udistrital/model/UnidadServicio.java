
package co.edu.udistrital.model;
import co.edu.udistrital.model.enums.TipoUnidad;
import co.edu.udistrital.model.enums.EstadoUnidad;
import java.util.UUID;

public class UnidadServicio {
    
    private String id;
    private TipoUnidad tipo;
    private EstadoUnidad estado;
    private String zona;
    private boolean disponibilidad; 

    public UnidadServicio(String id, TipoUnidad tipo, EstadoUnidad estado, String zona, boolean disponibilidad) {
        this.id = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.estado = EstadoUnidad.ACTIVO;
        this.zona = zona;
        this.disponibilidad = disponibilidad;
    }
    
    public String getId() {
        return id;
    }


    public TipoUnidad getTipo() {
        return tipo;
    }

    public void setTipo(TipoUnidad tipo) {
        this.tipo = tipo;
    }

    public EstadoUnidad getEstado() {
        return estado;
    }

    public void setEstado(EstadoUnidad estado) {
        this.estado = estado;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
    
    
}
