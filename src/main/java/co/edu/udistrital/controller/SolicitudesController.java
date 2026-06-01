/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.ListaEnlazadaSimple;
import co.edu.udistrital.model.enums.Cliente;
import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.model.SolicitudServicio;
import co.edu.udistrital.model.enums.*;
import co.edu.udistrital.view.PanelSolicitudes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Usuario
 */
public class SolicitudesController {

    private PanelSolicitudes vista;
    private Sistema sistema;

    public SolicitudesController(PanelSolicitudes vista, Sistema sistema) {
        this.vista = vista;
        this.sistema = sistema;
        inicializarEventos();
    }

    public void inicializarEventos() {

        vista.getCliente().removeAllItems();
        vista.getCliente().addItem(Cliente.ASEGURADORA.toString());
        vista.getCliente().addItem(Cliente.EMPRESA.toString());
        vista.getCliente().addItem(Cliente.PARTICULAR.toString());

        vista.getTipoServicio().removeAllItems();
        vista.getTipoServicio().addItem(TipoServicio.APERTURA_PUERTAS.toString());
        vista.getTipoServicio().addItem(TipoServicio.CAMBIO_LLANTA.toString());
        vista.getTipoServicio().addItem(TipoServicio.COMBUSTIBLE.toString());
        vista.getTipoServicio().addItem(TipoServicio.GRUA.toString());
        vista.getTipoServicio().addItem(TipoServicio.PASO_CORRIENTE.toString());
        vista.getTipoServicio().addItem(TipoServicio.REVISION_MECANICA.toString());

        vista.getZona().removeAllItems();
        vista.getZona().addItem(Zona.CENTRO.toString());
        vista.getZona().addItem(Zona.ESTE.toString());
        vista.getZona().addItem(Zona.NORTE.toString());
        vista.getZona().addItem(Zona.OESTE.toString());
        vista.getZona().addItem(Zona.SUR.toString());

        this.vista.getBotonGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarSolicitud();
            }
        });

        this.vista.getAtenderSiguienteBoton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sistema.atenderSiguienteSolicitud();
                } catch (IllegalArgumentException err){
                    vista.mostrarMensajeError(err.getMessage());
                }
            }
        });
    }

    public void guardarSolicitud() {
        String clienteSeleccionado = (String) vista.getCliente().getSelectedItem();  // Combobox llamada Cliente
        Cliente cliente = Cliente.valueOf(clienteSeleccionado);
        String tipoServicioSeleccionado = (String) vista.getTipoServicio().getSelectedItem(); // ComboBox llamada TipoServicio
        TipoServicio tipoServicio = TipoServicio.valueOf(tipoServicioSeleccionado);
        String descripcion = vista.getDescripcion().getText(); // TextArea llamado Descripcion
        String zonaSeleccionada = (String) vista.getZona().getSelectedItem(); // combo box llamado Zona 
        Zona zona = Zona.valueOf(zonaSeleccionada);
        sistema.registrarSolicitud(cliente, tipoServicio, descripcion, zona);
        System.out.println("solicitud registrada con exito");
    }

    public void actualizarProximasSolicitudes() {
        if (!sistema.getColaOrdinaria().estaVacia()) {
            SolicitudServicio ordinaria = sistema.getColaOrdinaria().peek();
            if (ordinaria != null) {
                String[] datos = {
                    ordinaria.getCliente().toString(),
                    ordinaria.getTipoServicio().toString(),
                    ordinaria.getZona().toString(),};
                vista.mostrarProximaOrdinaria(datos);
            } else {
                vista.mostrarProximaOrdinaria(null); // cola vacía
            }
        } else {
            vista.mostrarProximaOrdinaria(null);
        }

        if (!sistema.getColaCritica().estaVacia()) {
            SolicitudServicio critica = sistema.getColaCritica().verFrente();
            if (critica != null) {
                String[] datos = {
                    critica.getCliente().toString(),
                    critica.getTipoServicio().toString(),
                    critica.getZona().toString(),};
                vista.mostrarProximaCritica(datos);
            } else {
                vista.mostrarProximaCritica(null);
            }
        } else {
            vista.mostrarProximaCritica(null);
        }
    }
    
    public void mostrarSolicitudesEjecucion(){
        if (sistema.getListaEnEjecucion() == null){
            vista.mostrarTablaSolicitudes(new String[0][6]);
            return;
        }
        
        int n = sistema.getListaEnEjecucion().getTamanno();
        String[][] datos = new String[n][6];
        
        ListaEnlazadaSimple.Iterador<SolicitudServicio> it = sistema.getListaEnEjecucion().iterador();
        int i = 0;
        while(it.tieneSiguiente()){
            SolicitudServicio s = it.siguiente();
            datos[i][0] = s.getId();           // ID oculto
            datos[i][1] = s.getCliente().toString();     // cliente
            datos[i][2] = s.getTipoServicio().toString(); // servicio
            datos[i][3] = s.getZona().toString();         // zona
            datos[i][4] = s.getTecnico().getNombre();     // técnico
            datos[i][5] = s.getUnidadAsignada().getTipo().toString(); // unidad
            i++;
        }
    }
    
}
