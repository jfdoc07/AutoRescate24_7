/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;
import co.edu.udistrital.model.enums.*;

/**
 *
 * @author Home
 */
public class OperacionAtencionServicio extends Operacion {

    private SolicitudServicio solicitud;
    private UnidadServicio unidadServicio;
    private Tecnico tecnico;
    private Kit kit;

    public OperacionAtencionServicio(SolicitudServicio solicitud, UnidadServicio unidadServicio, Tecnico tecnico, Kit kit) {
        this.solicitud = solicitud;
        this.unidadServicio = unidadServicio;
        this.tecnico = tecnico;
        this.kit = kit;
    }

    @Override
    public void deshacer(Sistema sistema) {
        System.out.println("-> Ejecutando Rollback total de recursos implicados...");

        // 1. Resetear Técnico
        if (tecnico != null) {
            tecnico.setEstado(EstadoTecnico.DISPONIBLE);
            tecnico.setDisponibilidad(true);
        }

        // 2. Resetear Unidad
        if (unidadServicio != null) {
            unidadServicio.setEstado(EstadoUnidad.ACTIVO);
            unidadServicio.setDisponibilidad(true);
        }

        // 3. Devolver el Kit exacto a la pila de inventario
        if (solicitud.getKitAsignado() != null) {
            Kit kitUsado = solicitud.getKitAsignado();
            kitUsado.setCompleto(true);
            kitUsado.setRequiereReposicion(false);

            sistema.getPilaKits().apilar(kitUsado); // Regresa a la cima
            solicitud.setKitAsignado(null); // Desvincula
        }

        // 4. Mover la solicitud de regreso a las colas correspondientes
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        sistema.getListaEnEjecucion().eliminar(solicitud);

        if (solicitud.getCriticidad() == NivelCriticidad.CRITICA) {
            sistema.getColaCritica().insertar(solicitud);
        } else {
            sistema.getColaOrdinaria().enqueue(solicitud);
        }
    }

    @Override
    public String toString() {
        return "Solicitud puesta en ejecución. Descripción: " + solicitud.getDescripcion();
    }

}
