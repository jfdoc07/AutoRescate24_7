package co.edu.udistrital.model;

import java.util.UUID;

/**
 * Implementa una estructura de datos tipo pila genérica.
 * Los elementos se gestionan siguiendo el principio LIFO
 * (Last In, First Out), donde el último elemento agregado
 * es el primero en ser retirado.
 *
 * @param <T> Tipo de dato almacenado en la pila.
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class Pila<T> {

    /**
     * Representa un nodo interno utilizado por la pila.
     *
     * @param <T> Tipo de dato almacenado en el nodo.
     * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
     */
    private static class Nodo<T> {

        /**
         * Dato almacenado en el nodo.
         */
        T dato;

        /**
         * Referencia al siguiente nodo de la pila.
         */
        Nodo<T> siguiente;

        /**
         * Construye un nodo con el dato especificado.
         *
         * @param dato Dato que almacenará el nodo.
         */
        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    /**
     * Nodo ubicado en la cima de la pila.
     */
    private Nodo<T> cima;

    /**
     * Cantidad de elementos almacenados en la pila.
     */
    private int tamanio;

    /**
     * Construye una pila vacía.
     */
    public Pila() {
        cima = null;
        tamanio = 0;
    }

    /**
     * Inserta un elemento en la cima de la pila.
     *
     * @param dato Elemento que se desea agregar.
     * @throws IllegalArgumentException Si el dato es nulo.
     */
    public void apilar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se permiten elementos nulos.");
        }

        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamanio++;
    }

    /**
     * Elimina y retorna el elemento ubicado en la cima de la pila.
     *
     * @return Elemento retirado de la pila.
     * @throws RuntimeException Si la pila está vacía.
     */
    public T desapilar() {
        if (estaVacia()) {
            throw new RuntimeException("Pila vacía.");
        }

        T dato = cima.dato;
        cima = cima.siguiente;
        tamanio--;
        return dato;
    }

    /**
     * Obtiene el elemento ubicado en la cima sin eliminarlo.
     *
     * @return Elemento situado en la cima.
     * @throws RuntimeException Si la pila está vacía.
     */
    public T verCima() {
        if (estaVacia()) {
            throw new RuntimeException("No se puede ver la cima: la pila está vacía.");
        }

        return cima.dato;
    }

    /**
     * Verifica si la pila está vacía.
     *
     * @return true si no contiene elementos, false en caso contrario.
     */
    public boolean estaVacia() {
        return cima == null;
    }

    /**
     * Determina si un elemento se encuentra almacenado en la pila.
     *
     * @param dato Elemento a buscar.
     * @return true si el elemento existe, false en caso contrario.
     */
    public boolean contiene(T dato) {
        Nodo<T> actual = cima;

        while (actual != null) {
            if (actual.dato.equals(dato)) {
                return true;
            }

            actual = actual.siguiente;
        }

        return false;
    }

    /**
     * Crea un iterador para recorrer los elementos de la pila.
     *
     * @return Iterador asociado a la pila.
     */
    public Iterador<T> iterador() {
        return new Iterador<>(cima);
    }

    /**
     * Permite recorrer secuencialmente los elementos almacenados en la pila.
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
         * Construye un iterador a partir de la cima de la pila.
         *
         * @param cima Nodo inicial del recorrido.
         */
        public Iterador(Nodo<T> cima) {
            this.actual = cima;
        }

        /**
         * Verifica si existen más elementos por recorrer.
         *
         * @return true si hay más elementos, false en caso contrario.
         */
        public boolean tieneSiguiente() {
            return actual != null;
        }

        /**
         * Obtiene el siguiente elemento del recorrido.
         *
         * @return Siguiente elemento de la pila.
         * @throws IllegalStateException Si no existen más elementos.
         */
        public T siguiente() {
            if (!tieneSiguiente()) {
                throw new IllegalStateException("No hay más elementos");
            }

            T dato = actual.dato;
            actual = actual.siguiente;
            return dato;
        }
    }

    /**
     * Busca un elemento dentro de la pila utilizando su UUID.
     * El elemento debe implementar la interfaz Identificable.
     *
     * @param uuidBuscado UUID del elemento que se desea localizar.
     * @return Elemento encontrado o null si no existe.
     */
    public T obtenerPorUuid(UUID uuidBuscado) {
        if (uuidBuscado == null || cima == null) {
            return null;
        }

        Nodo<T> actual = cima;

        while (actual != null) {
            T datoActual = actual.dato;

            if (datoActual instanceof Identificable) {
                Identificable elemento = (Identificable) datoActual;

                if (elemento.getUuid().equals(uuidBuscado)) {
                    return datoActual;
                }
            }

            actual = actual.siguiente;
        }

        return null;
    }
}
