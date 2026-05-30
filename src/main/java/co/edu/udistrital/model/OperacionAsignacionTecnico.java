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

    @Override
    public String toString() {
        return "OperacionAsignacionTecnico{" + "tecnico=" + tecnico + ", solicitud=" + solicitud + '}';
    }
    
    
}
