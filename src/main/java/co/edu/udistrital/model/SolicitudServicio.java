package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.TipoServicio;
import co.edu.udistrital.model.enums.NivelCriticidad;
import co.edu.udistrital.model.enums.EstadoSolicitud;
import java.util.UUID;

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

    public int getPuntajeCriticidad() {
        return puntajeCriticidad;
    }

    public void setPuntajeCriticidad(int puntajeCriticidad) {
        this.puntajeCriticidad = puntajeCriticidad;
    }
    private int puntajeCriticidad; // se calcula al construir

    public SolicitudServicio(Cliente cliente, TipoServicio tipoServicio, String descripcion, NivelCriticidad criticidad, UnidadServicio unidadAsignada, Tecnico tecnico, Kit kitAsignado, EstadoSolicitud estado, String zona) {
        this.id = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.tipoServicio = tipoServicio;
        this.descripcion = descripcion;
        this.criticidad = criticidad;
        this.unidadAsignada = unidadAsignada;
        this.tecnico = tecnico;
        this.kitAsignado = kitAsignado;
        this.estado = estado;
        this.zona = zona;
    }

    public void setCriticidad(NivelCriticidad criticidad) {
        this.criticidad = criticidad;
    }

    public int calcularPuntaje() {
        int puntaje = 0;

        // Gravedad del servicio
        switch (tipoServicio) {
            case GRUA:              puntaje += 30; break;
            case REVISION_MECANICA: puntaje += 20; break;
            case PASO_CORRIENTE:    puntaje += 15; break;
            case CAMBIO_LLANTA:     puntaje += 10; break;
            case APERTURA_PUERTAS:  puntaje += 10; break;
            case COMBUSTIBLE:       puntaje +=  5; break;
        }

        // Tipo de cliente
        switch (cliente.getTipo()) {
            case "ASEGURADORA":         puntaje += 30; break;
            case "EMPRESA_TRANSPORTE":  puntaje += 20; break;
            case "PARTICULAR":          puntaje +=  5; break;
        }

        // Impacto vial
        if (zona != null && zona.toUpperCase().contains("VIA_PRINCIPAL")) {
            puntaje += 20;
        }

        return puntaje;
    }

    // El nivel sigue existiendo para saber si es crítica o no
    public NivelCriticidad calcularNivel() {
        return puntajeCriticidad >= 50 ? NivelCriticidad.CRITICA : NivelCriticidad.ORDINARIA;
    }

    // Este es el que usa ColaPrioridad para ordenar
    public int getPrioridad() {
        return puntajeCriticidad;
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return this.cliente;
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
