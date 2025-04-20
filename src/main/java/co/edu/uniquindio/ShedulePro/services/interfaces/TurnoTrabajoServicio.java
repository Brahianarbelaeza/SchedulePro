package co.edu.uniquindio.ShedulePro.services.interfaces;

import co.edu.uniquindio.ShedulePro.dto.turno.*;

import java.util.List;

public interface TurnoTrabajoServicio {
    String asignarTurno(AsignarTurnoDTO asignarTurnoDTO) throws Exception;
    String eliminarTurno(EliminarTurnoDTO eliminarTurnoDTO) throws Exception;
    String editarTurno(EditarTurnoDTO editarTurnoDTO) throws Exception;
    ItemTurnoTrabajoDTO obtenerTurno(ObtenerTurnoDTO obtenerTurnoDTO) throws Exception;
    List<ItemTurnoTrabajoDTO> listarTurnos() throws Exception;
    List<ItemTurnoTrabajoDTO> listarTurnosPorEmpleado(String idUsuario) throws Exception;
}
