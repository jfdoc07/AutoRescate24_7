package co.edu.udistrital.model;

/**
 * Implementa una cola de prioridad para gestionar solicitudes de servicio.
 * Los elementos se almacenan de forma ordenada según su nivel de prioridad,
 * de manera que las solicitudes con mayor prioridad se ubican al frente.
 *
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */
public class ColaPrioridad {

    /**
     * Nodo ubicado al frente de la cola de prioridad.
     */
    private Nodo<SolicitudServicio> frente;

    /**
     * Cantidad de elementos almacenados en la cola.
     */
    private int tamanio;

    /**
     * Construye una cola de prioridad vacía.
     */
    public ColaPrioridad() {
        this.frente = null;
        this.tamanio = 0;
    }

    /**
     * Inserta una solicitud en la cola manteniendo el orden de prioridad.
     *
     * @param solicitud Solicitud de servicio que se desea agregar.
     */
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

    /**
     * Extrae y retorna la solicitud con mayor prioridad.
     *
     * @return Solicitud de mayor prioridad o null si la cola está vacía.
     */
    public SolicitudServicio extraerMaxPrioridad() {
        if (estaVacia()) {
            return null;
        }

        SolicitudServicio dato = frente.getDato();
        frente = frente.getSiguiente();
        tamanio--;
        return dato;
    }

    /**
     * Obtiene la solicitud ubicada al frente de la cola sin eliminarla.
     *
     * @return Solicitud de mayor prioridad o null si la cola está vacía.
     */
    public SolicitudServicio verFrente() {
        if (estaVacia()) {
            return null;
        }
        return frente.getDato();
    }

    /**
     * Obtiene el nodo ubicado al frente de la cola.
     *
     * @return Nodo frontal de la cola.
     */
    public Nodo<SolicitudServicio> getFrente() {
        return frente;
    }

    /**
     * Obtiene la cantidad de elementos almacenados en la cola.
     *
     * @return Tamaño actual de la cola.
     */
    public int getTamanio() {
        return tamanio;
    }

    /**
     * Verifica si la cola de prioridad está vacía.
     *
     * @return true si no contiene elementos, false en caso contrario.
     */
    public boolean estaVacia() {
        return frente == null;
    }
}
