/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;
public class ColaPrioridad {

    private Nodo<SolicitudServicio> frente;
    private int tamanio;

    public ColaPrioridad() {
        this.frente = null;
        this.tamanio = 0;
    }

    public void insertar(SolicitudServicio solicitud) {
        Nodo<SolicitudServicio> nuevo = new Nodo<>(solicitud);

        if (frente == null || solicitud.getPrioridad() > frente.getDato().getPrioridad()) {
            nuevo.setSiguiente(frente);
            frente = nuevo;
            tamanio++;
            return;
        }

        Nodo<SolicitudServicio> actual = frente;
        while (actual.getSiguiente() != null
                && actual.getSiguiente().getDato().getPrioridad() >= solicitud.getPrioridad()) {
            actual = actual.getSiguiente();
        }

        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
        tamanio++;
    }

    public SolicitudServicio extraerMaxPrioridad() {
        if (estaVacia()) return null;
        SolicitudServicio dato = frente.getDato();
        frente = frente.getSiguiente();
        tamanio--;
        return dato;
    }

    public SolicitudServicio verFrente() {
        if (estaVacia()) return null;
        return frente.getDato();
    }

    public Nodo<SolicitudServicio> getFrente() {
        return frente;
    }

    public int getTamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return frente == null;
    }
}
