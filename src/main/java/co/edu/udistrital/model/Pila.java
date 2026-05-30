package co.edu.udistrital.model;

public class Pila<T> {

    private static class Nodo<T> {

        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo<T> cima;
    private int tamanio;

    public Pila() {
        cima = null;
        tamanio = 0;
    }

    public void apilar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se permiten elementos nulos.");
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamanio++;
    }

    public T desapilar() {
        if (estaVacia()) {
            throw new RuntimeException("Pila vacía.");
        }
        T dato = cima.dato;
        cima = cima.siguiente;
        tamanio--;
        return dato;
    }

    public T verCima() {
        if (estaVacia()) {
            throw new RuntimeException("No se puede ver la cima: la pila está vacía.");
        }
        return cima.dato;
    }

    public boolean estaVacia() {
        return cima == null;
    }

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

    public Iterador<T> iterador() {
        return new Iterador<>(cima);
    }

    public static class Iterador<T> {

        private Nodo<T> actual;

        public Iterador(Nodo<T> cima) {
            this.actual = cima;
        }

        public boolean tieneSiguiente() {
            return actual != null;
        }

        public T siguiente() {
            if (!tieneSiguiente()) {
                throw new IllegalStateException("No hay más elementos");
            }
            T dato = actual.dato;
            actual = actual.siguiente;
            return dato;
        }
    }
}
