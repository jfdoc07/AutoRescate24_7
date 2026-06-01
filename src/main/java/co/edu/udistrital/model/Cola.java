
package co.edu.udistrital.model;

import java.util.UUID;

public class Cola <Y>{

    private Nodo<Y> inicio;  
    private Nodo<Y> fin;    
    private int tamano;

    public Cola() {
        this.inicio = null;
        this.fin    = null;
        this.tamano = 0;
    }

   
    public void enqueue(Y dato) {
        Nodo<Y> nuevo = new Nodo<>(dato);

        if (estaVacia()) {
            inicio = nuevo;
            fin    = nuevo;
        } else {
            fin.setSiguiente(nuevo); 
            fin = nuevo;            
        }
        tamano++;
    }
    
    

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

    
    public Y peek() {
        if (estaVacia()) {
            throw new RuntimeException("La cola está vacía");
        }
        return inicio.getDato();
    }

    
    public boolean estaVacia() {
        return inicio == null;
    }

    public int getTamaño() {
        return tamano;
    }

    
    public Nodo<Y> getInicio() {
        return inicio;
    }

    public Nodo<Y> getFin() {
        return fin;
    }
    
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

    
    public String imprimir() {
        Nodo<Y> actual = inicio;
        String rta ="Inicio -> ";
        while (actual != null) {
            rta = rta + actual.getDato() + " -> ";
            actual = actual.getSiguiente();
        }
        rta += "null";
        return rta;
    }
}
