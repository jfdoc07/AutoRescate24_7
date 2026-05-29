package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.TipoServicio;
import co.edu.udistrital.model.enums.NivelCriticidad;
import co.edu.udistrital.model.enums.EstadoSolicitud;

public class SolicitudServicio {

    private String id;
    private Cliente cliente;
    private TipoServicio tipoServicio;
    private String descripcion;
    private NivelCriticidad criticidad;
    private UnidadServicio unidadAsignada;
    private Tecnico tecnico;
    private Kit kitAsignado;

    private EstadoSolicitud estado;
    private String zona;

    
    public int getPrioridad() {
        return this.criticidad == NivelCriticidad.CRITICA ? 2 : 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public NivelCriticidad getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(int personasHeridas, int viasAfectadas, int complejidad) {
        long puntajeCriticidad = personasHeridas * 5 + viasAfectadas + complejidad;
        if (0 <= puntajeCriticidad && puntajeCriticidad < 5) {
            criticidad = NivelCriticidad.ORDINARIA;
        } else if (5 <= puntajeCriticidad) {
            criticidad = NivelCriticidad.CRITICA;
        } else if (3 <= complejidad) {
            criticidad = NivelCriticidad.CRITICA;
        } else {
            criticidad = null;
        }
    }

    public UnidadServicio getUnidadAsignada() {
        return unidadAsignada;
    }

    public void setUnidadAsignada(UnidadServicio unidadAsignada) {
        this.unidadAsignada = unidadAsignada;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public void eliminarTecnico(Tecnico t) {
        if (tecnico != null && t.equals(this.tecnico)) {
            tecnico = null;
        }

    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

}
