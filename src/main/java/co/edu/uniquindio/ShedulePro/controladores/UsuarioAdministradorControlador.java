package co.edu.uniquindio.ShedulePro.controladores;


import co.edu.uniquindio.ShedulePro.dto.jws.MensajeDTO;
import co.edu.uniquindio.ShedulePro.dto.turno.*;
import co.edu.uniquindio.ShedulePro.dto.usuario.*;
import co.edu.uniquindio.ShedulePro.services.interfaces.TurnoTrabajoServicio;
import co.edu.uniquindio.ShedulePro.services.interfaces.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuario/administrador")
@RequiredArgsConstructor
public class UsuarioAdministradorControlador {

    private final UsuarioServicio usuarioServicio;
    private final TurnoTrabajoServicio turnoTrabajoServicio;

    @PostMapping("/crear-empleado")
    public ResponseEntity<MensajeDTO> crearEmpleado(@Valid @RequestBody CrearUsuarioDTO usuarioDTO) throws Exception {
        usuarioServicio.crearUsuario(usuarioDTO);
        return ResponseEntity.ok( new MensajeDTO<>(false, "Empleado creado correctamente"));
    }

    @PutMapping("/editar-empleado")
    public ResponseEntity<MensajeDTO> editarEmpleado(@Valid @RequestBody EditarUsuarioDTO usuarioDTO) throws Exception {
        usuarioServicio.editarUsuario(usuarioDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Empleado editado correctamente"));
    }

    @DeleteMapping("/eliminar-empleado")
    public ResponseEntity<MensajeDTO> eliminarEmpleado(@Valid @RequestBody EliminarUsuarioDTO usuarioDTO) throws Exception {
        usuarioServicio.eliminarUsuario(usuarioDTO.id());
        return ResponseEntity.ok(new MensajeDTO<>(false, "Empleado eliminado correctamente"));
    }

    @GetMapping ("/obtener-empleado/{id}")
    public ResponseEntity<MensajeDTO<InformacionUsuarioDTO>> obtenerEmpleado(@PathVariable String id) throws Exception {
        InformacionUsuarioDTO info = usuarioServicio.obtenerUsuario(id);
        return ResponseEntity.ok(new MensajeDTO<>( false, info));
    }

    @GetMapping("/listar-todo")
    public ResponseEntity<List<ItemUsuarioDTO>> listarUsuarios() throws Exception {
        List<ItemUsuarioDTO> usuarios = usuarioServicio.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    @PostMapping("/asignar-turno")
    public ResponseEntity<MensajeDTO> asignarTurno(@Valid @RequestBody AsignarTurnoDTO asignarTurnoDTO) throws Exception {
        turnoTrabajoServicio.asignarTurno(asignarTurnoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Turno asignado correctamente"));
    }
    @DeleteMapping("/eliminar-turno")
    public ResponseEntity<MensajeDTO> eliminarTurno(@Valid @RequestBody EliminarTurnoDTO eliminarTurnoDTO) throws Exception {
        turnoTrabajoServicio.eliminarTurno(eliminarTurnoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Turno eliminado correctamente"));
    }
    @PutMapping("/editar-turno")
    public ResponseEntity<MensajeDTO> editarTurno(@Valid @RequestBody EditarTurnoDTO editarTurnoDTO) throws Exception {
        turnoTrabajoServicio.editarTurno(editarTurnoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Turno editado correctamente"));
    }
    @GetMapping("/obtener-turno")
    public ResponseEntity<MensajeDTO> obtenerTurno(@Valid @RequestBody ObtenerTurnoDTO obtenerTurnoDTO) throws Exception {
        turnoTrabajoServicio.obtenerTurno(obtenerTurnoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Turno obtenido correctamente"));
    }
    @GetMapping("/listar-turnos")
    public ResponseEntity<List<ItemTurnoTrabajoDTO>> listarTurnos() throws Exception {
        List<ItemTurnoTrabajoDTO> turnos = turnoTrabajoServicio.listarTurnos();
        return ResponseEntity.ok(turnos);
    }
    @GetMapping("/listar-turnos-empleado/{id}")
    public ResponseEntity<List<ItemTurnoTrabajoDTO>> listarTurnosEmpleado(@PathVariable String id) throws Exception {
        List<ItemTurnoTrabajoDTO> turnos = turnoTrabajoServicio.listarTurnosPorEmpleado(id);
        return ResponseEntity.ok(turnos);
    }
}
