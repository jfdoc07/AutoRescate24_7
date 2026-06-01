/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.enums.Cliente;
import co.edu.udistrital.model.ListaEnlazadaSimple;
import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.model.Tecnico;
import co.edu.udistrital.model.UnidadServicio;
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
    }

    public void guardarSolicitud() {
        Cliente cliente = (Cliente) vista.getCliente().getSelectedItem();  // Combobox llamada Cliente
        TipoServicio tipoServicio = (TipoServicio) vista.getTipoServicio().getSelectedItem(); // ComboBox llamada TipoServicio
        String descripcion = vista.getDescripcion().getText(); // TextArea llamado Descripcion
        Zona zona = (Zona) vista.getZona().getSelectedItem(); // combo box llamado Zona
        sistema.registrarSolicitud(cliente, tipoServicio, descripcion, zona);
        System.out.println("solicitud registrada con exito");
    }

}
