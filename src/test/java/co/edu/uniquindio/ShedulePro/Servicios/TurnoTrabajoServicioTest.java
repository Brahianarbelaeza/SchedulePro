package co.edu.uniquindio.ShedulePro.Servicios;
import co.edu.uniquindio.ShedulePro.dto.turno.AsignarTurnoDTO;
import co.edu.uniquindio.ShedulePro.dto.turno.EliminarTurnoDTO;
import co.edu.uniquindio.ShedulePro.services.interfaces.TurnoTrabajoServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TurnoTrabajoServicioTest {
    @Autowired
    private TurnoTrabajoServicio turnoTrabajoServicio;

    @Test
    void asignarTurnoTest() {
        AsignarTurnoDTO asignarTurnoDTO = new AsignarTurnoDTO(
                "67cdf85709ed0637fa78bc4e",
                LocalDate.of(2025, 4, 19),
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                "Sede Principal"
        );
        assertDoesNotThrow(() -> {
            String id = turnoTrabajoServicio.asignarTurno(asignarTurnoDTO);
            assertNotNull(id, "El ID del usuario no debería ser nulo.");
        });
    }

    @Test
    void eliminarTurnoTrabajoTest() {
        EliminarTurnoDTO eliminarTurnoDTO = new EliminarTurnoDTO(
                "6802de0a2fd68f051d60ab4b"
        );
        assertDoesNotThrow(() -> {
            String resultado = turnoTrabajoServicio.eliminarTurno(eliminarTurnoDTO);
            assertEquals("Turno eliminado exitosamente", resultado, "El mensaje de éxito no coincide.");
        });
    }
}
