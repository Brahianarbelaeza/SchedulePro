package co.edu.uniquindio.ShedulePro.controladores;

import co.edu.uniquindio.ShedulePro.dto.turno.ItemTurnoTrabajoDTO;
import co.edu.uniquindio.ShedulePro.services.interfaces.TurnoTrabajoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/usuario/empleado")
@RequiredArgsConstructor
public class UsuarioEmpleadoControlador {

    private final TurnoTrabajoServicio turnoTrabajoServicio;

    @GetMapping("/listar-turnos-empleado/{id}")
    public ResponseEntity<List<ItemTurnoTrabajoDTO>> listarTurnosEmpleado(@PathVariable String id) throws Exception {
        List<ItemTurnoTrabajoDTO> turnos = turnoTrabajoServicio.listarTurnosPorEmpleado(id);
        return ResponseEntity.ok(turnos);
    }
}
