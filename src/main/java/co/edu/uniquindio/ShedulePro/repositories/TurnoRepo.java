package co.edu.uniquindio.ShedulePro.repositories;

import co.edu.uniquindio.ShedulePro.model.documents.TurnoTrabajo;
import co.edu.uniquindio.ShedulePro.model.enums.EstadoTurno;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface TurnoRepo extends MongoRepository<TurnoTrabajo, String> {
    boolean existsByFechaTurnoAndEmpleadoId(LocalDate fechaTurno, @NotBlank String s);
    List<TurnoTrabajo> findByFechaTurnoBeforeAndEstado(LocalDate fecha, EstadoTurno estado);

}
