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
<<<<<<< HEAD
        return this.cliente;
=======
        return cliente;
>>>>>>> d4ef097f82bc21d93324f9de9c4a2a207279024c
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

    public Kit getKitAsignado() {
        return kitAsignado;
    }

    public void setKitAsignado(Kit kitAsignado) {
        this.kitAsignado = kitAsignado;
    }

    
}
