/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.Operacion;
import co.edu.udistrital.model.Pila;
import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.view.PanelHistorial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;

/**
 *
 * @author Usuario
 */
public class HistorialController {

    private PanelHistorial vista;
    private Sistema sistema;

    public HistorialController(PanelHistorial vista, Sistema sistema) {
        this.vista = vista;
        this.sistema = sistema;
        verHistorial();
    }

    public void inicializarHistorial() {
        this.vista.getBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verHistorial();
            }
        });
    }
    public void verHistorial(){
        Pila<Operacion> historial = sistema.getHistorial();
        DefaultListModel<String> visual = new DefaultListModel<>();
        Pila.Iterador<Operacion> itOperaciones = sistema.getHistorial().iterador();
        while(itOperaciones.tieneSiguiente()){
            Operacion operacionActual = itOperaciones.siguiente();
            visual.addElement(operacionActual.toString());
        }
    }
}
