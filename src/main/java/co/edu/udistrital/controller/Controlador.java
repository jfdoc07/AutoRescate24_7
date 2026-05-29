/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.view.PanelTecnicos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Usuario
 */
public class Controlador implements ActionListener{
    PanelTecnicos view;
    Sistema model;
    
    public Controlador() {
        view = new PanelTecnicos();  //este es para pruebas
        model = new Sistema();
    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
