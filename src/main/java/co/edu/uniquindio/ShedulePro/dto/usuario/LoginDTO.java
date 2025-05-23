package co.edu.uniquindio.ShedulePro.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginDTO(
        @NotBlank @Email String email,
        @NotBlank @Length(min = 6, max = 20) String password
) {
}
