package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.*;

/**
 * Clase central que contiene toda la lógica y estructuras de datos del sistema AutoRescate 24/7.
 *
 * @author Santiago Aroca, Fernando Cárdenas, Daniel Molano
 */
public class Sistema {

    private Cola<SolicitudServicio> colaOrdinaria;
    private ColaPrioridad colaCritica;
    private Pila<Operacion> historial;
    private Pila<Kit> pilaKits;
    private ListaEnlazadaSimple<UnidadServicio> listaUnidades;
    private ListaEnlazadaSimple<Tecnico> listaTecnicos;
    private ListaEnlazadaSimple<Cliente> listaClientes;
    private ListaEnlazadaSimple<SolicitudServicio> listaCerradas;

    public Sistema() {
        colaOrdinaria   = new Cola<>();
        colaCritica     = new ColaPrioridad();
        historial       = new Pila<>();
        pilaKits        = new Pila<>();
        listaUnidades   = new ListaEnlazadaSimple<>();
        listaTecnicos   = new ListaEnlazadaSimple<>();
        listaClientes   = new ListaEnlazadaSimple<>();
        listaCerradas   = new ListaEnlazadaSimple<>();
    }

    public void añadirUnidad(TipoUnidad tipo, EstadoUnidad estado, String zona, boolean disponibilidad) {
        UnidadServicio unidad = new UnidadServicio(tipo, estado, zona, disponibilidad);
        listaUnidades.agregar(unidad);
    }

    public ListaEnlazadaSimple.Iterador<UnidadServicio> iteradorUnidades() {
        return listaUnidades.iterador();
    }

    public void actualizarUnidad(UnidadServicio unidad, TipoUnidad tipo, EstadoUnidad estado,
                                  String zona, boolean disponibilidad) {
        if (listaUnidades.contiene(unidad)) {
            unidad.setTipo(tipo);
            unidad.setEstado(estado);
            unidad.setZona(zona);
            unidad.setDisponibilidad(disponibilidad);
        }
    }

    public void eliminarUnidad(UnidadServicio unidad) {
        listaUnidades.eliminar(unidad);
    }
    
    public void añadirTecnico(String id, String nombre, String especialidad, boolean disponibilidad, EstadoTecnico estado){
        Tecnico tecnico = new Tecnico(id, nombre, especialidad, disponibilidad, estado);
        listaTecnicos.agregar(tecnico);
    }
    
    public void actualizarTecnico(Tecnico tecnico, String id, String nombre, String especialidad, boolean disponibilidad, EstadoTecnico estado){
        if(listaTecnicos.contiene(tecnico)) {
            tecnico.setId(id);
            tecnico.setNombre(nombre);
            tecnico.setEspecialidad(especialidad);
            tecnico.setDisponibilidad(disponibilidad);
            tecnico.setEstado(estado);
        }
    }
    
    public void eliminarTecnico(Tecnico tecnico){
        listaTecnicos.eliminar(tecnico);
    }
    
    public ListaEnlazadaSimple.Iterador<Tecnico> iteradorTecnicos(){
        return listaTecnicos.iterador();
    }
    
    public void añadirCliente(String id, String nombre, String tipo) {
    Cliente cliente = new Cliente(id, nombre, tipo);
    listaClientes.agregar(cliente);
    }

    public void actualizarCliente(Cliente cliente, String id, String nombre, String tipo) {
        if (listaClientes.contiene(cliente)) {
            cliente.setId(id);
            cliente.setNombre(nombre);
            cliente.setTipo(tipo);
        }
    }

    public void eliminarCliente(Cliente cliente) {
        listaClientes.eliminar(cliente);
    }

    public ListaEnlazadaSimple.Iterador<Cliente> iteradorClientes() {
        return listaClientes.iterador();
    }
}