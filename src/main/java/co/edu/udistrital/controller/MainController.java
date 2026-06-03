package co.edu.udistrital.controller;

import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.view.VentanaPrincipal;
import com.formdev.flatlaf.FlatDarkLaf;
import java.io.IOException;

public class MainController {

    private VentanaPrincipal ventana;
    private Sistema sistema;

    private TecnicosController tecnicosController;
    private SolicitudesController solicitudesController;
    private KitsController kitsController;
    private HistorialController historialController;

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
            kitsController.mostrarKitRevision();
            kitsController.mostrarProximoKit();
        });

        ventana.getBotonHistorial().addActionListener(e -> {
            ventana.mostrarPanel(ventana.getHistorial());
            historialController.verHistorial();
        });
    }
    
}
