/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.view.PanelKits;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Usuario
 */
public class KitsController {
    private PanelKits vista;
    private Sistema sistema;

    public KitsController(PanelKits vista, Sistema sistema) {
        this.vista = vista;
        this.sistema = sistema;
        inicializarKit();
    }
    
    public void inicializarKit(){
                this.vista.getBotonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarKit();
            }
        });
    }
    public void guardarKit(){
        String descripcion = vista.getDescripcion().getText(); //TextArea llamado descripcion
        sistema.añadirKit(descripcion, true, true);
    }
    

}
