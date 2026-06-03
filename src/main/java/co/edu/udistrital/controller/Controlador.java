
package co.edu.udistrital.controller;

import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.view.PanelTecnicos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener{
    PanelTecnicos view;
    Sistema model;
    
    public Controlador() {
        view = new PanelTecnicos();
        model = new Sistema();
    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
