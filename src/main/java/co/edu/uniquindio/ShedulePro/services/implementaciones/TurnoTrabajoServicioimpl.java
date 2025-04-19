package co.edu.uniquindio.ShedulePro.services.implementaciones;

import co.edu.uniquindio.ShedulePro.dto.turno.AsignarTurnoDTO;
import co.edu.uniquindio.ShedulePro.dto.turno.EditarTurnoDTO;
import co.edu.uniquindio.ShedulePro.dto.turno.EliminarTurnoDTO;
import co.edu.uniquindio.ShedulePro.dto.turno.ItemTurnoTrabajoDTO;
import co.edu.uniquindio.ShedulePro.model.documents.TurnoTrabajo;
import co.edu.uniquindio.ShedulePro.model.enums.EstadoTurno;
import co.edu.uniquindio.ShedulePro.repositories.TurnoRepo;
import co.edu.uniquindio.ShedulePro.repositories.UsuarioRepo;
import co.edu.uniquindio.ShedulePro.services.interfaces.TurnoTrabajoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoTrabajoServicioimpl implements TurnoTrabajoServicio {
    private final TurnoRepo asignarTurnoDTO;
    private final UsuarioRepo usuarioRepo;
    private final TurnoRepo turnoRepo;

    @Override
    public String asignarTurno(AsignarTurnoDTO asignarTurnoDTO) throws Exception {
        if (!usuarioRepo.existsById(asignarTurnoDTO.idEmpleado())) {
            throw new Exception("El empleado al que se le desea asignar horario no existe");
        }
        if (asignarTurnoDTO.horaEntrada().isAfter(asignarTurnoDTO.horaSalida()) || asignarTurnoDTO.horaEntrada().equals(asignarTurnoDTO.horaSalida())) {
            throw new IllegalArgumentException("La hora de entrada debe ser antes de la hora de salida");
        }
        if (turnoRepo.existsByFechaTurnoAndEmpleadoId(asignarTurnoDTO.fechaTurno(), asignarTurnoDTO.idEmpleado())) {
            throw new Exception("El empleado ya tiene un turno asignado para esa fecha");
        }
        TurnoTrabajo turnoTrabajo = new TurnoTrabajo(
                asignarTurnoDTO.idEmpleado(),
                asignarTurnoDTO.fechaTurno(),
                asignarTurnoDTO.horaEntrada(),
                asignarTurnoDTO.horaSalida(),
                asignarTurnoDTO.sede()
        );
        turnoTrabajo.setEstado(EstadoTurno.ACTIVO);
        turnoRepo.save(turnoTrabajo);
        return turnoTrabajo.getId();
    }

    @Override
    public String eliminarTurno(EliminarTurnoDTO eliminarTurnoDTO) throws Exception {
        if (!turnoRepo.existsById(eliminarTurnoDTO.idTurno())) {
            throw new Exception("El turno no existe");
        }
        turnoRepo.deleteById(eliminarTurnoDTO.idTurno());
        return "Turno eliminado exitosamente";
    }

    @Override
    public String editarTurno(String idTurno, EditarTurnoDTO editarTurnoDTO) throws Exception {
        return "";
    }

    @Override
    public List<ItemTurnoTrabajoDTO> listarTurnos() throws Exception {
        return List.of();
    }

    @Override
    public List<ItemTurnoTrabajoDTO> listarTurnosPorEmpleado(String idUsuario) throws Exception {
        return List.of();
    }
}

