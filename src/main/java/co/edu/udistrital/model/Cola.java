package co.edu.udistrital.model;

import java.util.UUID;

/**
 * Implementa una estructura de datos tipo cola genérica utilizando nodos enlazados.
 * Permite almacenar elementos siguiendo el principio FIFO (First In, First Out).
 * Además, proporciona métodos para consultar, insertar, eliminar y buscar elementos.
 *
 * @param <Y> Tipo de dato que almacenará la cola.
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class Cola<Y> {

    /**
     * Referencia al primer nodo de la cola.
     */
    private Nodo<Y> inicio;

    /**
     * Referencia al último nodo de la cola.
     */
    private Nodo<Y> fin;

    /**
     * Cantidad de elementos almacenados en la cola.
     */
    private int tamano;

    /**
     * Construye una cola vacía.
     */
    public Cola() {
        this.inicio = null;
        this.fin = null;
        this.tamano = 0;
    }

    /**
     * Inserta un nuevo elemento al final de la cola.
     *
     * @param dato Elemento que se desea agregar.
     */
    public void enqueue(Y dato) {
        Nodo<Y> nuevo = new Nodo<>(dato);

        if (estaVacia()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamano++;
    }

    /**
     * Elimina y retorna el elemento ubicado al inicio de la cola.
     *
     * @return Elemento eliminado o null si la cola está vacía.
     */
    public Y dequeue() {
        if (estaVacia()) {
            return null;
        }
        Y dato = inicio.getDato();
        inicio = inicio.getSiguiente();

        if (inicio == null) {
            fin = null;
        }
        tamano--;
        return dato;
    }

    /**
     * Obtiene el elemento ubicado al inicio de la cola sin eliminarlo.
     *
     * @return Elemento ubicado al inicio de la cola.
     * @throws RuntimeException Si la cola está vacía.
     */
    public Y peek() {
        if (estaVacia()) {
            throw new RuntimeException("La cola está vacía");
        }
        return inicio.getDato();
    }

    /**
     * Inserta un elemento al inicio de la cola.
     * Si la cola está vacía, el elemento se agrega normalmente.
     *
     * @param dato Elemento que se desea colocar al inicio.
     */
    public void setPeek(Y dato) {
        if (estaVacia()) {
            enqueue(dato);
        } else {
            Nodo<Y> nuevo = new Nodo<>(dato);
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
            tamano++;
        }
    }

    /**
     * Verifica si la cola se encuentra vacía.
     *
     * @return true si no contiene elementos, false en caso contrario.
     */
    public boolean estaVacia() {
        return inicio == null;
    }

    /**
     * Obtiene la cantidad de elementos almacenados en la cola.
     *
     * @return Tamaño actual de la cola.
     */
    public int getTamaño() {
        return tamano;
    }

    /**
     * Obtiene el nodo inicial de la cola.
     *
     * @return Nodo ubicado al inicio.
     */
    public Nodo<Y> getInicio() {
        return inicio;
    }

    /**
     * Obtiene el nodo final de la cola.
     *
     * @return Nodo ubicado al final.
     */
    public Nodo<Y> getFin() {
        return fin;
    }

    /**
     * Busca un elemento dentro de la cola utilizando su UUID.
     * El elemento debe implementar la interfaz Identificable.
     *
     * @param uuidBuscado UUID del elemento que se desea localizar.
     * @return Elemento encontrado o null si no existe.
     */
    public Y obtenerPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null || inicio == null) {
            return null;
        }

        Nodo<Y> actual = inicio;

        while (actual != null) {
            Y datoActual = actual.getDato();

            if (datoActual instanceof Identificable) {
                Identificable elemento = (Identificable) datoActual;

                if (elemento.getUuid().equals(uuidBuscado)) {
                    return datoActual;
                }
            }
            actual = actual.getSiguiente();
        }

        return null;
    }

    /**
     * Genera una representación en texto de los elementos de la cola.
     *
     * @return Cadena con el contenido completo de la cola.
     */
    public String imprimir() {
        Nodo<Y> actual = inicio;
        String rta = "Inicio -> ";
        while (actual != null) {
            rta = rta + actual.getDato() + " -> ";
            actual = actual.getSiguiente();
        }
        rta += "null";
        return rta;
    }
}
