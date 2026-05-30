/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.model.enums.EstadoTecnico;
import co.edu.udistrital.view.PanelTecnicos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TecnicosController {

    private PanelTecnicos vista;
    private Sistema sistema;

    public TecnicosController(PanelTecnicos vista, Sistema sistema) {
        this.vista = vista;
        this.sistema = sistema;

        inicializarEventos();
    }

    private void inicializarEventos() {

        this.vista.getBotonGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarTecnico();
            }
        });

    }

    private void guardarTecnico() {
        String nombre = vista.getNombre().getText();  //Es un JTextField
        String especialidad = vista.getEspecialidad().getText();  // es un JComboBox
        if (nombre != null && especialidad != null) {
            sistema.añadirTecnico(nombre, especialidad, true, EstadoTecnico.ASIGNADO);
        }
    }
}
