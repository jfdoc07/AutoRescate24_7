package co.edu.udistrital.model;
import co.edu.udistrital.model.enums.TipoUnidad;
import co.edu.udistrital.model.enums.EstadoUnidad;
import co.edu.udistrital.model.enums.Zona;
import java.util.UUID;

public class UnidadServicio implements Identificable{
    
    private UUID id;
    private TipoUnidad tipo;
    private EstadoUnidad estado;
    private Zona zona;
    private boolean disponibilidad; 

    public UnidadServicio(TipoUnidad tipo, EstadoUnidad estado, Zona zona, boolean disponibilidad) {
        this.id = UUID.randomUUID();
        this.tipo = tipo;
        this.estado = EstadoUnidad.ACTIVO;
        this.zona = zona;
        this.disponibilidad = disponibilidad;
    }
    
    @Override
    public UUID getUuid() {
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

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
    @Override
    public String toString(){
        return tipo.toString();
    }
    
}
