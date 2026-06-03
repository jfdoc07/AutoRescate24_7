package co.edu.udistrital.model;

import java.util.UUID;

/**
 * Implementa una lista enlazada simple genérica.
 * Permite almacenar elementos de cualquier tipo y realizar operaciones
 * básicas como agregar, eliminar, actualizar, recorrer y buscar elementos.
 *
 * @param <T> Tipo de dato almacenado en la lista.
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class ListaEnlazadaSimple<T> {

    /**
     * Nodo inicial de la lista.
     */
    private Nodo<T> cabeza;

    /**
     * Cantidad de elementos almacenados en la lista.
     */
    private int tamanno;

    /**
     * Construye una lista enlazada vacía.
     */
    public ListaEnlazadaSimple() {
        cabeza = null;
        tamanno = 0;
    }

    /**
     * Agrega un elemento al final de la lista.
     *
     * @param dato Elemento que se desea agregar.
     */
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
        tamanno++;
    }

    /**
     * Elimina la primera ocurrencia del elemento indicado.
     *
     * @param dato Elemento que se desea eliminar.
     * @return true si el elemento fue eliminado, false en caso contrario.
     */
    public boolean eliminar(T dato) {
        if (cabeza == null) {
            return false;
        }

        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            tamanno--;
            return true;
        }

        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null && !actual.getSiguiente().getDato().equals(dato)) {
            actual = actual.getSiguiente();
        }

        if (actual.getSiguiente() != null) {
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
            tamanno--;
            return true;
        }

        return false;
    }

    /**
     * Reemplaza un elemento existente por uno nuevo.
     *
     * @param datoViejo Elemento que será reemplazado.
     * @param datoNuevo Nuevo elemento que ocupará su lugar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizar(T datoViejo, T datoNuevo) {
        Nodo<T> actual = cabeza;

        while (actual != null) {
            if (actual.getDato().equals(datoViejo)) {
                actual.setDato(datoNuevo);
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
    }

    /**
     * Verifica si un elemento se encuentra almacenado en la lista.
     *
     * @param dato Elemento a buscar.
     * @return true si el elemento existe, false en caso contrario.
     */
    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;

        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
    }

    /**
     * Determina si la lista está vacía.
     *
     * @return true si no contiene elementos, false en caso contrario.
     */
    public boolean estaVacia() {
        return cabeza == null;
    }

    /**
     * Obtiene la cantidad de elementos almacenados en la lista.
     *
     * @return Tamaño actual de la lista.
     */
    public int getTamanno() {
        return tamanno;
    }

    /**
     * Imprime los elementos de la lista en consola.
     */
    public void imprimir() {
        Nodo<T> actual = cabeza;

        while (actual != null) {
            System.out.print(actual + (actual.getSiguiente() != null ? " -> " : ""));
            actual = actual.getSiguiente();
        }

        System.out.println();
    }

    /**
     * Crea un iterador para recorrer los elementos de la lista.
     *
     * @return Iterador asociado a la lista.
     */
    public Iterador<T> iterador() {
        return new Iterador<>(cabeza);
    }

    /**
     * Permite recorrer secuencialmente los elementos de la lista enlazada.
     *
     * @param <T> Tipo de dato almacenado.
     * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
     */
    public static class Iterador<T> {

        /**
         * Nodo actual del recorrido.
         */
        private Nodo<T> actual;

        /**
         * Construye un iterador a partir del nodo inicial.
         *
         * @param cabeza Nodo desde donde comenzará el recorrido.
         */
        public Iterador(Nodo<T> cabeza) {
            this.actual = cabeza;
        }

        /**
         * Verifica si existen más elementos por recorrer.
         *
         * @return true si hay elementos disponibles, false en caso contrario.
         */
        public boolean tieneSiguiente() {
            return actual != null;
        }

        /**
         * Obtiene el siguiente elemento del recorrido.
         *
         * @return Siguiente elemento de la lista.
         * @throws IllegalStateException Si no existen más elementos.
         */
        public T siguiente() {
            if (!tieneSiguiente()) {
                throw new IllegalStateException("No hay más elementos");
            }

            T dato = actual.getDato();
            actual = actual.getSiguiente();
            return dato;
        }
    }

    /**
     * Busca un elemento en la lista utilizando su UUID.
     * El elemento debe implementar la interfaz Identificable.
     *
     * @param uuidBuscado UUID del elemento que se desea localizar.
     * @return Elemento encontrado o null si no existe.
     */
    public T obtenerPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null || cabeza == null) {
            return null;
        }

        Nodo<T> actual = cabeza;

        while (actual != null) {
            T datoActual = actual.getDato();

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
}
