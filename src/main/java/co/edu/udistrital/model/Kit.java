package co.edu.udistrital.model;

import java.util.UUID;

/**
 * Representa un kit dentro del sistema.
 * Cada kit posee un identificador único, una descripción y estados que
 * indican si está completo y si requiere reposición.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class Kit implements Identificable {

    /**
     * Identificador único del kit.
     */
    private UUID id;

    /**
     * Descripción del kit.
     */
    private String descripcion;

    /**
     * Indica si el kit está completo.
     */
    private boolean completo;

    /**
     * Indica si el kit requiere reposición.
     */
    private boolean requiereReposicion;

    /**
     * Construye un nuevo kit con los datos especificados.
     *
     * @param descripcion Descripción del kit.
     * @param completo Estado que indica si el kit está completo.
     * @param requiereReposicion Estado que indica si el kit requiere reposición.
     */
    public Kit(String descripcion, boolean completo, boolean requiereReposicion) {
        this.id = UUID.randomUUID();
        this.descripcion = descripcion;
        this.completo = completo;
        this.requiereReposicion = requiereReposicion;
    }

    /**
     * Obtiene el identificador único del kit.
     *
     * @return UUID asociado al kit.
     */
    @Override
    public UUID getUuid() {
        return id;
    }

    /**
     * Asigna un nuevo identificador al kit.
     *
     * @param id Nuevo UUID del kit.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Obtiene la descripción del kit.
     *
     * @return Descripción del kit.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripción del kit.
     *
     * @param descripcion Nueva descripción del kit.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Indica si el kit está completo.
     *
     * @return true si está completo, false en caso contrario.
     */
    public boolean isCompleto() {
        return completo;
    }

    /**
     * Modifica el estado de completitud del kit.
     *
     * @param completo Nuevo estado del kit.
     */
    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    /**
     * Indica si el kit requiere reposición.
     *
     * @return true si requiere reposición, false en caso contrario.
     */
    public boolean isRequiereReposicion() {
        return requiereReposicion;
    }

    /**
     * Modifica el estado de reposición del kit.
     *
     * @param requiereReposicion Nuevo estado de reposición.
     */
    public void setRequiereReposicion(boolean requiereReposicion) {
        this.requiereReposicion = requiereReposicion;
    }
}
