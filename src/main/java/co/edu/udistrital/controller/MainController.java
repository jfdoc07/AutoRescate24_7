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
import com.formdev.flatlaf.FlatDarkLaf;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class MainController {

    private VentanaPrincipal ventana;
    private Sistema sistema;

    // Sub-controladores
    private TecnicosController tecnicosController;
    private SolicitudesController solicitudesController;
    private KitsController kitsController;
    private HistorialController historialController;
    private DashboardController dashboardController;

    public MainController() {
        FlatDarkLaf.setup();

        this.sistema = new Sistema();
        this.ventana = new VentanaPrincipal();

        inicializarControladores();
        conectarNavegacion();
    }

    private void inicializarControladores() {
        this.tecnicosController = new TecnicosController(ventana.getTecnicos(), sistema);
        this.solicitudesController = new SolicitudesController(ventana.getSolicitudes(), sistema);
        this.kitsController = new KitsController(ventana.getKits(), sistema);
        this.historialController = new HistorialController(ventana.getHistorial(), sistema);
        this.dashboardController = new DashboardController(ventana.getDashboard(), sistema);
    }

    public void iniciar() {
        ventana.setVisible(true);
    }

    private void conectarNavegacion() {
        ventana.getBotonDashboard().addActionListener(e -> {
            try {
                sistema.csv();
                System.exit(0);
            } catch (IOException ex) {
                
            }
        });

        ventana.getBotonTecnicos().addActionListener(e -> {
            ventana.mostrarPanel(ventana.getTecnicos());
            tecnicosController.actualizarTabla();
        });
        ventana.getBotonSolicitudes().addActionListener(e -> {
            ventana.mostrarPanel(ventana.getSolicitudes());
            solicitudesController.actualizarProximasSolicitudes();
            solicitudesController.mostrarSolicitudesEjecucion();
        });

        ventana.getBotonKits().addActionListener(e -> {
            ventana.mostrarPanel(ventana.getKits());
            kitsController.mostrarUnidades();
            kitsController.mostrarKits();
            kitsController.mostrarProximoKit();
        });

        ventana.getBotonHistorial().addActionListener(e -> {
            ventana.mostrarPanel(ventana.getHistorial());
            /*historialController.actualizarPila();*/
        });
    }
    
}
