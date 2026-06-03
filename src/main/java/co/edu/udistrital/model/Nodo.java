package co.edu.udistrital.model;

/**
 * Representa un nodo genérico utilizado en las estructuras de datos enlazadas
 * del sistema. Cada nodo almacena un dato y una referencia al siguiente nodo.
 *
 * @param <T> Tipo de dato almacenado en el nodo.
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class Nodo<T> {

    /**
     * Dato almacenado en el nodo.
     */
    public T dato;

    /**
     * Referencia al siguiente nodo de la estructura enlazada.
     */
    public Nodo<T> siguiente;

    /**
     * Construye un nodo vacío.
     * El dato y la referencia al siguiente nodo se inicializan en null.
     */
    public Nodo() {
        this.dato = null;
        this.siguiente = null;
    }

    /**
     * Construye un nodo con el dato especificado.
     *
     * @param dato Dato que almacenará el nodo.
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    /**
     * Obtiene el dato almacenado en el nodo.
     *
     * @return Dato contenido en el nodo.
     */
    public T getDato() {
        return dato;
    }

    /**
     * Modifica el dato almacenado en el nodo.
     *
     * @param dato Nuevo dato del nodo.
     */
    public void setDato(T dato) {
        this.dato = dato;
    }

    /**
     * Obtiene la referencia al siguiente nodo.
     *
     * @return Nodo siguiente.
     */
    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    /**
     * Establece la referencia al siguiente nodo.
     *
     * @param siguiente Nuevo nodo siguiente.
     */
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}