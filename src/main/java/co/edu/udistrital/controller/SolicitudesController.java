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

        vista.getCliente().addItem(Cliente.ASEGURADORA);
        vista.getCliente().addItem(Cliente.EMPRESA);
        vista.getCliente().addItem(Cliente.PARTICULAR);

        vista.getTipoServicio().addItem(TipoServicio.APERTURA_PUERTAS);
        vista.getTipoServicio().addItem(TipoServicio.CAMBIO_LLANTA);
        vista.getTipoServicio().addItem(TipoServicio.COMBUSTIBLE);
        vista.getTipoServicio().addItem(TipoServicio.GRUA);
        vista.getTipoServicio().addItem(TipoServicio.PASO_CORRIENTE);
        vista.getTipoServicio().addItem(TipoServicio.REVISION_MECANICA);

        while (sistema.iteradorTecnicos().tieneSiguiente()) {
            Tecnico tecnicoActual = sistema.iteradorTecnicos().siguiente();
            vista.getTecnico().addItem(tecnicoActual);
        }

        vista.getZona().addItem(Zona.CENTRO);
        vista.getZona().addItem(Zona.ESTE);
        vista.getZona().addItem(Zona.NORTE);
        vista.getZona().addItem(Zona.OESTE);
        vista.getZona().addItem(Zona.SUR);

        this.vista.getBotonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarSolicitud();
            }
        });
        this.vista.getBotonActualizarCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarOpciones();
            }
        });
    }

    public void guardarSolicitud() {
        Cliente cliente = (Cliente) vista.getCliente().getSelectedItem();  // Combobox llamada Cliente
        TipoServicio tipoServicio = (TipoServicio) vista.getTipoServicio().getSelectedItem(); // ComboBox llamada TipoServicio
        String descripcion = vista.getDescripcion().getText(); // TextArea llamado Descripcion
        Tecnico tecnico = (Tecnico) vista.getTecnico().getSelectedItem(); // ComboBox llamado Tecnico
        Zona zona = (Zona) vista.getZona().getSelectedItem(); // combo box llamado Zona
        sistema.registrarSolicitud(cliente, tipoServicio, descripcion, tecnico, zona);
    }

    public void actualizarOpciones() {
        vista.getTecnico().removeAllItems();

        while (sistema.iteradorTecnicos().tieneSiguiente()) {
            Tecnico tecnicoActual = sistema.iteradorTecnicos().siguiente();
            vista.getTecnico().addItem(tecnicoActual);
        }
    }
}
