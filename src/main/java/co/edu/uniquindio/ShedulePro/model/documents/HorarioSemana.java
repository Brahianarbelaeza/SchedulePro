package co.edu.uniquindio.ShedulePro.model.documents;

import co.edu.uniquindio.ShedulePro.model.Dia;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("usuario")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class HorarioSemana {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private ObjectId usuarioId;
    private Dia[] semana;
    private double nomina;
}
