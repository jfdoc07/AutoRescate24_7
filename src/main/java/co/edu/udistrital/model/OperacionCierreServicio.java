package co.edu.udistrital.model;

import co.edu.udistrital.model.enums.*;

/**
 *
 * @author Usuario
 */
public class OperacionCierreServicio extends Operacion {

    private SolicitudServicio solicitud;
    private UnidadServicio unidadServicio;
    private Tecnico tecnico;
    private Kit kit;

    public OperacionCierreServicio(SolicitudServicio solicitud) {
        this.solicitud = solicitud;
        unidadServicio = solicitud.getUnidadAsignada();
        tecnico = solicitud.getTecnico();
        kit = solicitud.getKitAsignado();
    }

    @Override
    public void deshacer(Sistema sistema) {
        System.out.println("-> Deshaciendo Cierre de Servicio: Reabriendo solicitud...");
        
        if (tecnico != null) {
            tecnico.setEstado(EstadoTecnico.ASIGNADO);
            tecnico.setDisponibilidad(false);
        }

        if (unidadServicio != null) {
            unidadServicio.setEstado(EstadoUnidad.OCUPADO);
            unidadServicio.setDisponibilidad(false);
        }

        if (kit != null) {
            sistema.getPilaKitsRevision().desapilar();
        }

        solicitud.setEstado(EstadoSolicitud.EN_EJECUCION);

        sistema.getListaCerradas().eliminar(solicitud);
        sistema.getListaEnEjecucion().agregar(solicitud);

        this.solicitud.setEstado(EstadoSolicitud.EN_EJECUCION);
    }

    @Override
    public String toString() {
        return "Solicitud cerrada. Descripción" + solicitud.getDescripcion();
    }

}
