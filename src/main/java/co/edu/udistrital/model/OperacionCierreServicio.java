package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.EstadoSolicitud;

/**
 *
 * @author Usuario
 */
public class OperacionCierreServicio extends Operacion{
    private SolicitudServicio solicitud;

    public OperacionCierreServicio(SolicitudServicio solicitud) {
        this.solicitud = solicitud;
    }

    @Override
    public void deshacer() {
        this.solicitud.setEstado(EstadoSolicitud.EN_EJECUCION);
    }
    
    
    
}
