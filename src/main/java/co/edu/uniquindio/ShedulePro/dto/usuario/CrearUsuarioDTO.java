package co.edu.uniquindio.ShedulePro.dto.usuario;

import co.edu.uniquindio.ShedulePro.model.enums.Cargo;
import co.edu.uniquindio.ShedulePro.model.enums.Departamento;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record CrearUsuarioDTO (

        @NotBlank @Length(max = 10) String cedula,
        @NotBlank @Length (max = 100)String nombre,
        @NotBlank @Length (max = 100)String apellido,
        @NotBlank @Length (max = 10)String telefono,
        @NotBlank @Email String email,
        @NotNull Departamento departamento,
        @NotNull Cargo cargo,
        @NotNull @Past LocalDateTime fechaContratacion
        ){

}
