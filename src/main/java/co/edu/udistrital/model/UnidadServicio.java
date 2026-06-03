package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.TipoUnidad;
import co.edu.udistrital.model.enums.EstadoUnidad;
import co.edu.udistrital.model.enums.Zona;
import java.util.UUID;

/**
 * Representa una unidad de servicio disponible para atender solicitudes
 * dentro del sistema. Cada unidad posee un tipo, estado operativo,
 * zona de cobertura y disponibilidad.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class UnidadServicio implements Identificable {

    /**
     * Identificador único de la unidad.
     */
    private UUID id;

    /**
     * Tipo de unidad de servicio.
     */
    private TipoUnidad tipo;

    /**
     * Estado actual de la unidad.
     */
    private EstadoUnidad estado;

    /**
     * Zona en la que opera la unidad.
     */
    private Zona zona;

    /**
     * Indica si la unidad se encuentra disponible para ser asignada.
     */
    private boolean disponibilidad;

    /**
     * Construye una nueva unidad de servicio.
     *
     * @param tipo Tipo de unidad.
     * @param estado Estado inicial de la unidad.
     * @param zona Zona de operación.
     * @param disponibilidad Disponibilidad inicial de la unidad.
     */
    public UnidadServicio(TipoUnidad tipo, EstadoUnidad estado, Zona zona, boolean disponibilidad) {
        this.id = UUID.randomUUID();
        this.tipo = tipo;
        this.estado = EstadoUnidad.ACTIVO;
        this.zona = zona;
        this.disponibilidad = disponibilidad;
    }

    /**
     * Obtiene el identificador único de la unidad.
     *
     * @return UUID asociado a la unidad.
     */
    @Override
    public UUID getUuid() {
        return id;
    }

    /**
     * Obtiene el tipo de unidad.
     *
     * @return Tipo de unidad.
     */
    public TipoUnidad getTipo() {
        return tipo;
    }

    /**
     * Modifica el tipo de unidad.
     *
     * @param tipo Nuevo tipo de unidad.
     */
    public void setTipo(TipoUnidad tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el estado actual de la unidad.
     *
     * @return Estado de la unidad.
     */
    public EstadoUnidad getEstado() {
        return estado;
    }

    /**
     * Modifica el estado de la unidad.
     *
     * @param estado Nuevo estado de la unidad.
     */
    public void setEstado(EstadoUnidad estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la zona de operación de la unidad.
     *
     * @return Zona asignada.
     */
    public Zona getZona() {
        return zona;
    }

    /**
     * Modifica la zona de operación de la unidad.
     *
     * @param zona Nueva zona asignada.
     */
    public void setZona(Zona zona) {
        this.zona = zona;
    }

    /**
     * Indica si la unidad está disponible.
     *
     * @return true si está disponible, false en caso contrario.
     */
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Modifica la disponibilidad de la unidad.
     *
     * @param disponibilidad Nuevo estado de disponibilidad.
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     * Obtiene una representación textual de la unidad.
     *
     * @return Nombre del tipo de unidad.
     */
    @Override
    public String toString() {
        return tipo.toString();
    }
}
