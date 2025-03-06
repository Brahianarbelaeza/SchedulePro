package co.edu.uniquindio.ShedulePro.model.documents;
import co.edu.uniquindio.ShedulePro.model.enums.Cargo;
import co.edu.uniquindio.ShedulePro.model.enums.Departamento;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Document("usuario")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Usuario {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private String telefono;
    private String email;
    private String password;
    private LocalDateTime fechaContratacion;
    private Departamento departamento;
    private Cargo cargo;
    private HorarioSemana horarioSemana;

}
