/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.ListaEnlazadaSimple;
import co.edu.udistrital.model.Sistema;
import co.edu.udistrital.model.Tecnico;
import co.edu.udistrital.model.enums.EstadoTecnico;
import co.edu.udistrital.model.enums.TipoServicio;
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

        vista.getEspecialidad().removeAllItems();
        vista.getEspecialidad().addItem(TipoServicio.APERTURA_PUERTAS.toString());
        vista.getEspecialidad().addItem(TipoServicio.CAMBIO_LLANTA.toString());
        vista.getEspecialidad().addItem(TipoServicio.COMBUSTIBLE.toString());
        vista.getEspecialidad().addItem(TipoServicio.GRUA.toString());
        vista.getEspecialidad().addItem(TipoServicio.PASO_CORRIENTE.toString());
        vista.getEspecialidad().addItem(TipoServicio.REVISION_MECANICA.toString());

        this.vista.getBotonGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vista.mostrarMensaje(guardarTecnico());
                } catch (IllegalArgumentException err) {
                    vista.mostrarMensaje(err.getMessage());
                }
            }
        });

    }

    private String guardarTecnico() {
        String nombre = vista.getNombre().getText().trim();  //Es un JTextField
        String especialidadStr = vista.getEspecialidad().getSelectedItem().toString();  // es un JComboBox
        TipoServicio especialidad = TipoServicio.valueOf(especialidadStr); // convierte a enum
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("Tiene que rellenar todos los campos.");
        }
        sistema.añadirTecnico(nombre, especialidad, true, EstadoTecnico.DISPONIBLE);
        return "Tecnico guardado correctamente";
    }

    public void actualizarTabla() {
        if (sistema.getListaEnEjecucion() == null) {
            vista.llenarTablaTecnicos(new String[0][4]);
            return;
        }

        int n = sistema.getListaTecnicos().getTamanno();
        String[][] datos = new String[n][4];

        ListaEnlazadaSimple.Iterador<Tecnico> it = sistema.getListaTecnicos().iterador();
        int i = 0;
        while (it.tieneSiguiente()) {
            Tecnico t = it.siguiente();
            datos[i][0] = t.getNombre();
            datos[i][1] = t.getEspecialidad().toString();
            datos[i][2] = t.getEstado().toString();
            datos[i][3] = t.isDisponibilidad() ? "Sí" : "No";
            i++;
        }

        vista.llenarTablaTecnicos(datos);
    }
}
