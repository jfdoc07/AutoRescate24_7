package co.edu.udistrital.model;

import java.util.UUID;

public class ListaEnlazadaSimple<T> {

    private Nodo<T> cabeza;
    private int tamanno;

    public ListaEnlazadaSimple() {
        cabeza = null;
        tamanno = 0;
    }

    
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

    
    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamanno() {
        return tamanno;
    }

    public void imprimir() {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            System.out.print(actual + (actual.getSiguiente() != null ? " -> " : ""));
            actual = actual.getSiguiente();
        }
        System.out.println();
    }

    
    public Iterador<T> iterador() {
        return new Iterador<>(cabeza);
    }

    public static class Iterador<T> {

        private Nodo<T> actual;

        public Iterador(Nodo<T> cabeza) {
            this.actual = cabeza;
        }

        public boolean tieneSiguiente() {
            return actual != null;
        }

        public T siguiente() {
            if (!tieneSiguiente()) {
                throw new IllegalStateException("No hay más elementos");
            }
            T dato = actual.getDato();
            actual = actual.getSiguiente();
            return dato;
        }

    }
    
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
