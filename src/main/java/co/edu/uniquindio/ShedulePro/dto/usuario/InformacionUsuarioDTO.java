package co.edu.uniquindio.ShedulePro.dto.usuario;

import co.edu.uniquindio.ShedulePro.model.enums.Cargo;
import co.edu.uniquindio.ShedulePro.model.enums.Departamento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record InformacionUsuarioDTO(
        @NotBlank String id,
        @NotBlank @Length(max = 10) String cedula,
        @NotBlank @Length (max = 100)String nombre,
        @NotBlank @Length (max = 100)String apellido,
        @NotBlank @Length (max = 10)String telefono,
        @NotBlank @Email String email,
        @NotNull Departamento departamento,
        @NotNull Cargo cargo,
        @NotNull @Past LocalDateTime fechaContratacion
) {
}
