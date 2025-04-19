package co.edu.uniquindio.ShedulePro.model.documents;

import co.edu.uniquindio.ShedulePro.model.enums.EstadoTurno;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document("turno")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TurnoTrabajo {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String empleadoId;
    private LocalDate fechaTurno;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private String sede;
    private EstadoTurno estado; // ACTIVO, INACTIVO, LIQUIDADO

    @Builder
    public TurnoTrabajo (String empleadoId, LocalDate fechaTurno, LocalTime horaEntrada, LocalTime horaSalida, String sede) {
        this.empleadoId = empleadoId;
        this.fechaTurno = fechaTurno;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.sede = sede;
        this.estado = estado;
    }
}

