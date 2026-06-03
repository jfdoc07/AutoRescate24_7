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

        // 1. Volver a ocupar al Técnico asignado
        if (tecnico != null) {
            tecnico.setEstado(EstadoTecnico.ASIGNADO);
            tecnico.setDisponibilidad(false); // Vuelve a estar ocupado trabajando
        }

        // 2. Volver a ocupar la Unidad de Servicio
        if (unidadServicio != null) {
            unidadServicio.setEstado(EstadoUnidad.OCUPADO);
            unidadServicio.setDisponibilidad(false); // Vuelve a estar en ruta/operación
        }

        // 3. 🚨 Manejo del Kit de herramientas
        if (kit != null) {
            // Como la solicitud se cerró, el kit se había enviado a la lista general de revisión/kits.
            // Debemos sacarlo de esa lista para que vuelva a estar vinculado exclusivamente a la ejecución.
            sistema.getListaKits().eliminar(kit);
        }

        // 4. Cambiar el estado de la solicitud a EN_EJECUCION
        solicitud.setEstado(EstadoSolicitud.EN_EJECUCION);

        // 5. Mover entre estructuras de datos de la aplicación
        sistema.getListaCerradas().eliminar(solicitud);   // Sale del histórico de terminados
        sistema.getListaEnEjecucion().agregar(solicitud); // Reingresa al panel de control activo

        this.solicitud.setEstado(EstadoSolicitud.EN_EJECUCION);
    }

    @Override
    public String toString() {
        return "Solicitud cerrada. Descripción" + solicitud.getDescripcion();
    }

}
