package co.edu.uniquindio.ShedulePro.services.implementaciones;

import co.edu.uniquindio.ShedulePro.dto.turno.*;
import co.edu.uniquindio.ShedulePro.model.documents.TurnoTrabajo;
import co.edu.uniquindio.ShedulePro.model.enums.EstadoTurno;
import co.edu.uniquindio.ShedulePro.repositories.TurnoRepo;
import co.edu.uniquindio.ShedulePro.repositories.UsuarioRepo;
import co.edu.uniquindio.ShedulePro.services.interfaces.TurnoTrabajoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurnoTrabajoServicioimpl implements TurnoTrabajoServicio {
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
    public String editarTurno(EditarTurnoDTO editarTurnoDTO) throws Exception {
        if (!turnoRepo.existsById(editarTurnoDTO.idTurno())) {
            throw new Exception("El turno no existe");
        }
        if (editarTurnoDTO.horaEntrada().isAfter(editarTurnoDTO.horaSalida()) || editarTurnoDTO.horaEntrada().equals(editarTurnoDTO.horaSalida())) {
            throw new IllegalArgumentException("La hora de entrada debe ser antes de la hora de salida");
        }
        TurnoTrabajo turnoTrabajo = turnoRepo.findById(editarTurnoDTO.idTurno()).orElseThrow(() -> new Exception("El turno no existe"));
        turnoTrabajo.setHoraEntrada(editarTurnoDTO.horaEntrada());
        turnoTrabajo.setHoraSalida(editarTurnoDTO.horaSalida());
        turnoTrabajo.setSede(editarTurnoDTO.sede());
        turnoRepo.save(turnoTrabajo);
        return "Turno editado exitosamente";
    }

    @Override
    @Transactional(readOnly = true)
    public ItemTurnoTrabajoDTO obtenerTurno(ObtenerTurnoDTO obtenerTurnoDTO) throws Exception {
        if (!turnoRepo.existsById(obtenerTurnoDTO.idTurno())) {
            throw new Exception("El turno no existe");
        }
        TurnoTrabajo turnoTrabajo = turnoRepo.findById(obtenerTurnoDTO.idTurno()).orElseThrow(() -> new Exception("El turno no existe"));
        return new ItemTurnoTrabajoDTO(
                turnoTrabajo.getEmpleadoId(),
                turnoTrabajo.getId(),
                usuarioRepo.findById(turnoTrabajo.getEmpleadoId()).orElseThrow(() -> new Exception("El empleado no existe")).getNombre(),
                turnoTrabajo.getFechaTurno(),
                turnoTrabajo.getHoraEntrada(),
                turnoTrabajo.getHoraSalida(),
                turnoTrabajo.getSede()
        );
    }

    @Override
    public List<ItemTurnoTrabajoDTO> listarTurnos() {
        return turnoRepo.findAll().stream()
                .filter(turnoTrabajo -> turnoTrabajo.getEstado().equals(EstadoTurno.ACTIVO))
                .map(turnoTrabajo -> {
                    try {
                        return new ItemTurnoTrabajoDTO(
                                turnoTrabajo.getEmpleadoId(),
                                turnoTrabajo.getId(),
                                usuarioRepo.findById(turnoTrabajo.getEmpleadoId()).orElseThrow(() -> new Exception("El empleado no existe")).getNombre(),
                                turnoTrabajo.getFechaTurno(),
                                turnoTrabajo.getHoraEntrada(),
                                turnoTrabajo.getHoraSalida(),
                                turnoTrabajo.getSede());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    //Metodo empleado
    @Override
    public List<ItemTurnoTrabajoDTO> listarTurnosPorEmpleado(String idUsuario) throws Exception {
        if (!usuarioRepo.existsById(idUsuario)) {
            throw new Exception("El empleado no existe");
        }
        return turnoRepo.findAll().stream()
                .filter(turnoTrabajo -> turnoTrabajo.getEmpleadoId().equals(idUsuario) && turnoTrabajo.getEstado().equals(EstadoTurno.ACTIVO))
                .map(turnoTrabajo -> {
                    try {
                        return new ItemTurnoTrabajoDTO(
                                turnoTrabajo.getEmpleadoId(),
                                turnoTrabajo.getId(),
                                usuarioRepo.findById(turnoTrabajo.getEmpleadoId()).orElseThrow(() -> new Exception("El empleado no existe")).getNombre(),
                                turnoTrabajo.getFechaTurno(),
                                turnoTrabajo.getHoraEntrada(),
                                turnoTrabajo.getHoraSalida(),
                                turnoTrabajo.getSede());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    @Scheduled (cron = "0 0 0 * * *")  // Todos los días a las 12:00 AM
    public void actualizarEstadosTurnosVencidos() {
        LocalDate hoy = LocalDate.now();

        List<TurnoTrabajo> turnosVencidos = turnoRepo.findByFechaTurnoBeforeAndEstado(hoy, EstadoTurno.ACTIVO);

        for (TurnoTrabajo turno : turnosVencidos) {
            turno.setEstado(EstadoTurno.INACTIVO);
        }

        turnoRepo.saveAll(turnosVencidos);

        System.out.println("Se actualizaron " + turnosVencidos.size() + " turnos vencidos.");
    }

}

