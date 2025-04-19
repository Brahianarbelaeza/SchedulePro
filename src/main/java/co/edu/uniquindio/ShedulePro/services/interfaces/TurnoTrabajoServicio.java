package co.edu.uniquindio.ShedulePro.services.interfaces;

import co.edu.uniquindio.ShedulePro.dto.turno.AsignarTurnoDTO;
import co.edu.uniquindio.ShedulePro.dto.turno.EditarTurnoDTO;
import co.edu.uniquindio.ShedulePro.dto.turno.EliminarTurnoDTO;
import co.edu.uniquindio.ShedulePro.dto.turno.ItemTurnoTrabajoDTO;

import java.util.List;

public interface TurnoTrabajoServicio {
    String asignarTurno(AsignarTurnoDTO asignarTurnoDTO) throws Exception;
    String eliminarTurno(EliminarTurnoDTO eliminarTurnoDTO) throws Exception;
    String editarTurno(String idTurno, EditarTurnoDTO editarTurnoDTO) throws Exception;
    List<ItemTurnoTrabajoDTO> listarTurnos() throws Exception;
    List<ItemTurnoTrabajoDTO> listarTurnosPorEmpleado(String idUsuario) throws Exception;
}
