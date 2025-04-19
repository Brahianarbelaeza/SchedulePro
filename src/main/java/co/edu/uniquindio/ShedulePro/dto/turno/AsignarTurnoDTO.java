package co.edu.uniquindio.ShedulePro.dto.turno;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record AsignarTurnoDTO(
        @NotBlank String idEmpleado,
        @NotNull @FutureOrPresent LocalDate fechaTurno,
        @NotNull LocalTime horaEntrada,
        @NotNull LocalTime horaSalida,
        @NotBlank @Size(max = 100) String sede
) {}
