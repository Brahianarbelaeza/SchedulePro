package co.edu.uniquindio.ShedulePro.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record EliminarUsuarioDTO(
        @NotBlank String id
) {
}
