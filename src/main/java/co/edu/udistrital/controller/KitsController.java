/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

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
        String descripcion = vista.getDescripcion().getText(); //TextArea llamado descripcion
        sistema.añadirKit(descripcion, true, true);
        System.out.println("kit guardado correctamente");
    }

    public void guardarUnidad() {
        String tipoUnidadSeleccionada = (String) vista.getTipoUnidad().getSelectedItem();
        TipoUnidad tipoUnidad = TipoUnidad.valueOf(tipoUnidadSeleccionada);
        String zonaSeleccionada = (String) vista.getZona().getSelectedItem(); // combo box llamado Zona 
        Zona zona = Zona.valueOf(zonaSeleccionada);
        sistema.añadirUnidad(tipoUnidad, zona);
        System.out.println("Unidad guardada" + tipoUnidad + zona);
    }

    public void mostrarUnidades() {
        if (sistema.getListaUnidades() == null) {
            vista.mostrarTablaUnidades(new String[0][4]);
            return;
        }

        int n = sistema.getListaUnidades().getTamanno();
        String[][] datos = new String[n][4];

        ListaEnlazadaSimple.Iterador<UnidadServicio> it = sistema.getListaUnidades().iterador();
        int i = 0;
        while (it.tieneSiguiente()) {
            UnidadServicio u = it.siguiente();
            datos[i][0] = u.getTipo().toString();
            datos[i][1] = u.getEstado().toString();
            datos[i][2] = u.getZona().toString();
            if (u.isDisponibilidad()) {
                    datos[i][3] = "DISPONIBLE";
            } else {
                datos[i][3] = "OCUPADO";
            }
            i++;
        }
        vista.mostrarTablaUnidades(datos);
    }
}
