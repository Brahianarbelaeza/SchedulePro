package co.edu.uniquindio.ShedulePro.dto.turno;

import jakarta.validation.constraints.NotBlank;

public record EliminarTurnoDTO(
        @NotBlank String idTurno
) {
}
