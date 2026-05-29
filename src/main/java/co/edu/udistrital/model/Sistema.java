package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.*;

/**
 * Clase que modela la lógica del sistema.
 *
 * @author Usuario
 */
public class Sistema {

    private Cola<SolicitudServicio> colaOrdinaria;
    private ColaPrioridad colaCritica;
    private Pila<Operacion> historial;
    private Pila<Kit> pilaKit;
    private ListaEnlazadaSimple listaUnidades;

    public void ejecucion() {

    }


    
    
}
