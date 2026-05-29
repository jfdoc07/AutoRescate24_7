/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.EstadoTecnico;

/**
 *
 * @author Usuario
 */
public class OperacionAsignacionTecnico extends Operacion{
    private Tecnico tecnico;
    private SolicitudServicio solicitud;

    public OperacionAsignacionTecnico(Tecnico tecnico, SolicitudServicio solicitud) {
        this.tecnico = tecnico;
        this.solicitud = solicitud;
    }

    @Override
    public void deshacer() {
        solicitud.eliminarTecnico(tecnico);
        tecnico.setDisponibilidad(true);
    }
    
    
}
