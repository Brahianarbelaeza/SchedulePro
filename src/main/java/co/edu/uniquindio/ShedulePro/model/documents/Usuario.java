package co.edu.uniquindio.ShedulePro.model.documents;
import co.edu.uniquindio.ShedulePro.model.enums.Cargo;
import co.edu.uniquindio.ShedulePro.model.enums.Departamento;
import co.edu.uniquindio.ShedulePro.model.enums.Estado;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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
    private String telefono;
    private String email;
    private String password;
    private LocalDateTime fechaContratacion;
    private Departamento departamento;
    private Cargo cargo;
    private Estado estado;
    private HorarioSemana horarioSemana;

    //CONSTRUCTOR BUILDER
    @Builder
    public Usuario (String cedula,String nombre,String apellido,String telefono,String email ,Departamento departamento, Cargo cargo,LocalDateTime fechaContratacion ) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.email = email;
        this.telefono = telefono;
        this.fechaContratacion = fechaContratacion;
        this.departamento = departamento;
        this.cargo = cargo;

    }

}
