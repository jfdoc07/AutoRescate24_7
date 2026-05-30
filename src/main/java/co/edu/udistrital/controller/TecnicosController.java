/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.Sistema;
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
        // Aquí agregamos los ActionListeners a los botones de la vista.
        // Asumiendo que le pongas un getter al botón en PanelTecnicos:
        /*
        this.vista.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarTecnico();
            }
        });
        */
    }
    
    private void guardarTecnico() {
        // 1. Extraer datos de la vista (ej. vista.getTxtNombre().getText())
        // 2. Validar que no estén vacíos
        // 3. Enviar al modelo: sistema.añadirTecnico(...)
        // 4. Limpiar los campos de la vista
        // 5. Actualizar la tabla de técnicos en la vista
    }
}
