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
            throw new RuntimeException("No se puede desapilar: la pila está vacía.");
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
        return tamanio == 0;
    }

    public int tamanio() {
        return tamanio;
    }

    public void vaciar() {
        cima = null;
        tamanio = 0;
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

    @Override
    public String toString() {
        if (estaVacia()) {
            return "Pila: []";
        }
        StringBuilder sb = new StringBuilder("Pila (cima → base): [");
        Nodo<T> actual = cima;
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) {
                sb.append(", ");
            }
            actual = actual.siguiente;
        }
        sb.append("]");
        return sb.toString();
    }
}
