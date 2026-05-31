/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package co.edu.udistrital.controller;
import com.formdev.flatlaf.FlatDarkLaf;
import co.edu.udistrital.view.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 * Clase encargada de lanzar la primera vez la ventana principal de todo el proyecto
 * @author Santiago Aroca, Fernando Cárdenas y Daniel Molano.
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainController().iniciar();
        });
    }
}
