package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.EstadoTecnico;
import co.edu.udistrital.model.enums.TipoServicio;
import java.util.UUID;

/**
 * Representa un técnico registrado en el sistema.
 * Cada técnico posee una especialidad, un estado operativo y una
 * disponibilidad que determina si puede ser asignado a una solicitud.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class Tecnico implements Identificable {

    /**
     * Identificador único del técnico.
     */
    private UUID id;

    /**
     * Nombre del técnico.
     */
    private String nombre;

    /**
     * Especialidad del técnico.
     */
    private TipoServicio especialidad;

    /**
     * Indica si el técnico se encuentra disponible para ser asignado.
     */
    private boolean disponibilidad;

    /**
     * Estado actual del técnico.
     */
    private EstadoTecnico estado;

    /**
     * Construye un nuevo técnico con la información especificada.
     *
     * @param nombre Nombre del técnico.
     * @param especialidad Especialidad del técnico.
     * @param disponibilidad Disponibilidad inicial del técnico.
     * @param estado Estado inicial del técnico.
     */
    public Tecnico(String nombre, TipoServicio especialidad, boolean disponibilidad, EstadoTecnico estado) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.disponibilidad = disponibilidad;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador único del técnico.
     *
     * @return UUID asociado al técnico.
     */
    @Override
    public UUID getUuid() {
        return id;
    }

    /**
     * Obtiene el nombre del técnico.
     *
     * @return Nombre del técnico.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del técnico.
     *
     * @param nombre Nuevo nombre del técnico.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la especialidad del técnico.
     *
     * @return Especialidad del técnico.
     */
    public TipoServicio getEspecialidad() {
        return especialidad;
    }

    /**
     * Modifica la especialidad del técnico.
     *
     * @param especialidad Nueva especialidad.
     */
    public void setEspecialidad(TipoServicio especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Indica si el técnico está disponible.
     *
     * @return true si está disponible, false en caso contrario.
     */
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Modifica la disponibilidad del técnico.
     *
     * @param disponibilidad Nuevo estado de disponibilidad.
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     * Obtiene el estado actual del técnico.
     *
     * @return Estado del técnico.
     */
    public EstadoTecnico getEstado() {
        return estado;
    }

    /**
     * Modifica el estado del técnico.
     *
     * @param estado Nuevo estado del técnico.
     */
    public void setEstado(EstadoTecnico estado) {
        this.estado = estado;
    }

    /**
     * Obtiene una representación textual del técnico.
     *
     * @return Nombre del técnico.
     */
    @Override
    public String toString() {
        return this.nombre;
    }
}