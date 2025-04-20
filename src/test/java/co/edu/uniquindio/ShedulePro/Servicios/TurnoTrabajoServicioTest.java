package co.edu.uniquindio.ShedulePro.Servicios;
import co.edu.uniquindio.ShedulePro.dto.turno.*;
import co.edu.uniquindio.ShedulePro.services.interfaces.TurnoTrabajoServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TurnoTrabajoServicioTest {
    @Autowired
    private TurnoTrabajoServicio turnoTrabajoServicio;

    @Test
    void asignarTurnoTest() {
        AsignarTurnoDTO asignarTurnoDTO = new AsignarTurnoDTO(
                "67cde31da0c37b25cabcce98",
                LocalDate.of(2025, 4, 21),
                LocalTime.of(9, 0),
                LocalTime.of(15, 0),
                "Sede Filandia"
        );
        assertDoesNotThrow(() -> {
            String id = turnoTrabajoServicio.asignarTurno(asignarTurnoDTO);
            assertNotNull(id, "El ID del usuario no debería ser nulo.");
        });
    }

    @Test
    void asignarTurnoExistenteTest() {
        AsignarTurnoDTO asignarTurnoDTO = new AsignarTurnoDTO(
                "67cdf85709ed0637fa78bc4e",
                LocalDate.of(2025, 4, 19),
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                "Sede 2"
        );
        assertThrows(Exception.class, () -> {
            turnoTrabajoServicio.asignarTurno(asignarTurnoDTO);
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
    @Test
    void eliminarTurnoTrabajoNoExistenteTest() {
        EliminarTurnoDTO eliminarTurnoDTO = new EliminarTurnoDTO(
                "6802de0a2fd68f051d60ab4b"
        );
        assertThrows(Exception.class, () -> {
            turnoTrabajoServicio.eliminarTurno(eliminarTurnoDTO);
        });
    }
    @Test
    void editarTurnoTrabajoTest(){
        EditarTurnoDTO editarTurnoDTO = new EditarTurnoDTO(
                "6803f2974887be1716584baf",
                LocalDate.of(2025, 4, 23),
                LocalTime.of(5, 0),
                LocalTime.of(13, 0),
                "Sede Calarca"
        );
        assertDoesNotThrow(() -> {
            String respuesta = turnoTrabajoServicio.editarTurno(editarTurnoDTO);
            assertEquals("Turno editado exitosamente", respuesta, "El mensaje de éxito no coincide.");
        });
    }
    @Test
    void obtenerTurnoTrabajoTest () {
        ObtenerTurnoDTO obtenerTurnoDTO = new ObtenerTurnoDTO("6803f2974887be1716584baf");
        assertDoesNotThrow(() -> {
            ItemTurnoTrabajoDTO respuesta = turnoTrabajoServicio.obtenerTurno(obtenerTurnoDTO);
            assertNotNull(respuesta, "La respuesta no debería ser nula.");
            System.out.println(respuesta.toString());
        });
    }
    @Test
    void listarTurnosTrabajoTest (){
        assertDoesNotThrow(() -> {
            List<ItemTurnoTrabajoDTO> respuesta = turnoTrabajoServicio.listarTurnos();
            assertNotNull(respuesta, "La lista de turnos no debería ser nula.");
            System.out.println(respuesta.toString());
        });
    }
    @Test
    void listarTurnosTrabajoPorEmpleadoTest (){
        String id = "67cde31da0c37b25cabcce98";
        assertDoesNotThrow(() -> {
            List<ItemTurnoTrabajoDTO> respuesta = turnoTrabajoServicio.listarTurnosPorEmpleado(id);
            assertNotNull(respuesta, "La lista de turnos no debería ser nula.");
            System.out.println(respuesta.toString());
        });
    }

}
