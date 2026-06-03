package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.Cliente;
import co.edu.udistrital.model.enums.TipoServicio;
import co.edu.udistrital.model.enums.NivelCriticidad;
import co.edu.udistrital.model.enums.EstadoSolicitud;
import co.edu.udistrital.model.enums.Zona;
import java.util.UUID;

public class SolicitudServicio implements Identificable{

    private UUID id;
    private Cliente cliente;
    private TipoServicio tipoServicio;
    private String descripcion;
    private NivelCriticidad criticidad;
    private UnidadServicio unidadAsignada;
    private Tecnico tecnico;
    private Kit kitAsignado;
    private EstadoSolicitud estado;
    private Zona zona;

    public int getPuntajeCriticidad() {
        return puntajeCriticidad;
    }

    public void setPuntajeCriticidad(int puntajeCriticidad) {
        this.puntajeCriticidad = puntajeCriticidad;
    }
    private int puntajeCriticidad;

    public SolicitudServicio(Cliente cliente, TipoServicio tipoServicio, String descripcion, NivelCriticidad criticidad, UnidadServicio unidadAsignada, Tecnico tecnico, Kit kitAsignado, EstadoSolicitud estado, Zona zona) {
        this.id = UUID.randomUUID();
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

        switch (tipoServicio) {
            case GRUA:
                puntaje += 30;
                break;
            case REVISION_MECANICA:
                puntaje += 20;
                break;
            case PASO_CORRIENTE:
                puntaje += 15;
                break;
            case CAMBIO_LLANTA:
                puntaje += 10;
                break;
            case APERTURA_PUERTAS:
                puntaje += 10;
                break;
            case COMBUSTIBLE:
                puntaje += 5;
                break;
        }

        switch (cliente) {
            case Cliente.ASEGURADORA:
                puntaje += 30;
                break;
            case Cliente.EMPRESA:
                puntaje += 20;
                break;
            case Cliente.PARTICULAR:
                puntaje += 5;
                break;
        }

        return puntaje;
    }

    public NivelCriticidad calcularNivel() {
        return puntajeCriticidad >= 35 ? NivelCriticidad.CRITICA : NivelCriticidad.ORDINARIA;
    }

    public int getPrioridad() {
        return puntajeCriticidad;
    }

    @Override
    public UUID getUuid() {
        return this.id;
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

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Kit getKitAsignado() {
        return kitAsignado;
    }

    public void setKitAsignado(Kit kitAsignado) {
        this.kitAsignado = kitAsignado;
    }
}
