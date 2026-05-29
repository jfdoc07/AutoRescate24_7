/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

/**
 *
 * @author Usuario
 */

import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.view.VentanaPrincipal;

public class MainController {
    
    private VentanaPrincipal ventana;
    private Sistema sistema;
    
    // Sub-controladores
    private TecnicosController tecnicosController;
    private SolicitudesController solicitudesController;
    private KitsController kitsController;
    private HistorialController historialController;
    private DashboardController dashboardController;

    public MainController(VentanaPrincipal ventana, Sistema sistema) {
        this.ventana = ventana;
        this.sistema = sistema;
        
        inicializarControladores();
    }
    
    private void inicializarControladores() {
        // Aquí conectamos cada panel específico con el sistema central
        // Nota: Necesitarás crear métodos "getters" en VentanaPrincipal 
        // para acceder a cada panel, ej: ventana.getPanelTecnicos()
        
        this.tecnicosController = new TecnicosController(ventana.getPanelTecnicos(), sistema);
        this.solicitudesController = new SolicitudesController(ventana.getPanelSolicitudes(), sistema);
        this.kitsController = new KitsController(ventana.getPanelKits(), sistema);
        this.historialController = new HistorialController(ventana.getPanelHistorial(), sistema);
        this.dashboardController = new DashboardController(ventana.getPanelDashboard(), sistema);
    }
    
    public void iniciar() {
        ventana.setVisible(true);
    }
}