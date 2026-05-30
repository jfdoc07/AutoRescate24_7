/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.view.PanelDashboard;

/**
 *
 * @author Usuario
 */
public class DashboardController {
    private PanelDashboard vista;
    private Sistema sistema;

    public DashboardController(PanelDashboard vista, Sistema sistema) {
        this.vista = vista;
        this.sistema = sistema;
        inicializarEventos();
    }
    
    public void inicializarEventos(){
        //aquí poner todo
    }
}
