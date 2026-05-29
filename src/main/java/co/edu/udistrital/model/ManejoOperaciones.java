/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;

/**
 *
 * @author Usuario
 */
public class ManejoOperaciones {

    public boolean apilarOperacion(Operacion operacion) {
        try {
            Pila<Operacion> pilaOperaciones = new Pila();
            pilaOperaciones.apilar(operacion);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    

}
