/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.Kit;
import co.edu.udistrital.model.ListaEnlazadaSimple;
import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.model.UnidadServicio;
import co.edu.udistrital.model.enums.TipoUnidad;
import co.edu.udistrital.model.enums.Zona;
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
        inicializarEventos();
    }

    public void inicializarEventos() {
        this.vista.getBotonGuardarKit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarKit();
            }
        });

        this.vista.getGuardarUnidad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarUnidad();
            }
        });

        this.vista.getEnviarSacarMantenimiento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarSacarMantenimiento();
            }
        });

        this.vista.getEnviarAPila().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                añadirAPila();
            }
        });

        vista.getTipoUnidad().removeAllItems();
        vista.getTipoUnidad().addItem(TipoUnidad.CAMIONETA.toString());
        vista.getTipoUnidad().addItem(TipoUnidad.MOTO.toString());
        vista.getTipoUnidad().addItem(TipoUnidad.GRUA.toString());
        vista.getTipoUnidad().addItem(TipoUnidad.VEHICULO_LIVIANO.toString());

        vista.getZona().removeAllItems();
        vista.getZona().addItem(Zona.CENTRO.toString());
        vista.getZona().addItem(Zona.ESTE.toString());
        vista.getZona().addItem(Zona.NORTE.toString());
        vista.getZona().addItem(Zona.OESTE.toString());
        vista.getZona().addItem(Zona.SUR.toString());
    }

    public void guardarKit() {
        String descripcion = vista.getDescripcion().getText();
        sistema.añadirKit(descripcion, true, false);
        vista.mostrarMensaje("Kit guardado correctamente");
    }

    public void guardarUnidad() {
        String tipoUnidadSeleccionada = (String) vista.getTipoUnidad().getSelectedItem();
        TipoUnidad tipoUnidad = TipoUnidad.valueOf(tipoUnidadSeleccionada);
        String zonaSeleccionada = (String) vista.getZona().getSelectedItem();
        Zona zona = Zona.valueOf(zonaSeleccionada);
        sistema.añadirUnidad(tipoUnidad, zona);
        vista.mostrarMensaje("Unidad guardada " + tipoUnidad + " " + zona);
    }

    public void enviarSacarMantenimiento() {
        try {
            String idUnidad = vista.getIdUnidadSeleccionada();
            vista.mostrarMensaje(sistema.enviarSacarMantenimiento(idUnidad));
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje(e.getMessage());
        }
    }

    public void mostrarUnidades() {
        if (sistema.getListaUnidades() == null) {
            vista.mostrarTablaUnidades(new String[0][5]);
            return;
        }

        int n = sistema.getListaUnidades().getTamanno();
        String[][] datos = new String[n][5];

        ListaEnlazadaSimple.Iterador<UnidadServicio> it = sistema.getListaUnidades().iterador();
        int i = 0;
        while (it.tieneSiguiente()) {
            UnidadServicio u = it.siguiente();
            datos[i][0] = u.getUuid().toString();
            datos[i][1] = u.getTipo().toString();
            datos[i][2] = u.getEstado().toString();
            datos[i][3] = u.getZona().toString();
            datos[i][4] = u.isDisponibilidad() ? "DISPONIBLE" : "OCUPADO";
            i++;
        }
        vista.mostrarTablaUnidades(datos);
    }

    public void mostrarKitRevision() {
        if (!sistema.getPilaKitsRevision().estaVacia() || !sistema.getPilaKitsRevision().estaVacia()) {
            Kit k = sistema.getPilaKitsRevision().verCima();
            if (k != null) {
                String[] datos = new String[3];
                datos[0] = k.getDescripcion();
                datos[1] = k.isCompleto() ? "Si" : "No";
                datos[2] = k.isRequiereReposicion() ? "Si" : "No";
                vista.mostrarKitRevision(datos);
            } else {
                vista.mostrarKitRevision(null);
            }
        } else {
            vista.mostrarKitRevision(null);
        }
    }

    public void añadirAPila() {
        try {
            vista.mostrarMensaje(sistema.enviarKitAPila());
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje(e.getMessage());
        }
    }

    public void mostrarProximoKit() {
        if (!sistema.getPilaKits().estaVacia()) {
            Kit k = sistema.getPilaKits().verCima();
            if (k != null) {
                String desc = k.getDescripcion();
                vista.mostrarProximoKit(desc);
            } else {
                vista.mostrarProximoKit(null);
            }
        } else {
            vista.mostrarProximoKit(null);
        }
    }
}
