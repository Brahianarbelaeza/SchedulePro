package co.edu.uniquindio.ShedulePro.Servicios;

import co.edu.uniquindio.ShedulePro.Config.JWTUtils;
import co.edu.uniquindio.ShedulePro.dto.LoginDTO;
import co.edu.uniquindio.ShedulePro.dto.TokenDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.CrearUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.EditarUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.InformacionUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.ItemUsuarioDTO;
import co.edu.uniquindio.ShedulePro.model.enums.Cargo;
import co.edu.uniquindio.ShedulePro.model.enums.Departamento;
import co.edu.uniquindio.ShedulePro.services.interfaces.UsuarioServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UsuarioServicioTest {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private JWTUtils jwtUtils;
    @Test
    void crearUsuarioTest() {
        CrearUsuarioDTO crearUsuarioDTO = new CrearUsuarioDTO(
                "1234567890",
                "Juan",
                "Pérez",
                "3111234567",
                "barbelaezaguirre@gmail.com",
                Departamento.AMAZONAS,
                Cargo.EMPLEADO,
                LocalDateTime.of(2022, 1, 1, 10, 0)
        );

        assertDoesNotThrow(() -> {
            String id = usuarioServicio.crearUsuario(crearUsuarioDTO);
            assertNotNull(id, "El ID del usuario no debería ser nulo.");
        });
    }

    @Test
    void editarUsuarioTest() {
        EditarUsuarioDTO editarUsuarioDTO = new EditarUsuarioDTO(
                "605c77c8e0e1f8b3a8b7f123",
                "1234567890",
                "Juan",
                "Pérez Modificado",
                "3121234567",
                "juan.modificado@example.com",
                Departamento.ANTIOQUIA,
                Cargo.EMPLEADO,
                LocalDateTime.of(2022, 2, 1, 10, 0)
        );

        assertDoesNotThrow(() -> {
            String id = usuarioServicio.editarUsuario(editarUsuarioDTO);
            assertNotNull(id, "El ID del usuario no debería ser nulo.");
        });
    }

    @Test
    void eliminarUsuarioTest() {
        String id = "67cbc3ecf31a832bd9984d8d";

        assertDoesNotThrow(() -> {
            String respuesta = usuarioServicio.eliminarUsuario(id);
            assertNotNull(respuesta, "La respuesta no debería ser nula.");
        });
    }

    @Test
    void obtenerUsuarioTest() {
        String id = "605c77c8e0e1f8b3a8b7f123";

        assertDoesNotThrow(() -> {
            InformacionUsuarioDTO usuario = usuarioServicio.obtenerUsuario(id);
            assertNotNull(usuario, "La información del usuario no debería ser nula.");
        });
    }

    @Test
    void listarUsuariosTest() {
        assertDoesNotThrow(() -> {
            List<ItemUsuarioDTO> usuarios = usuarioServicio.listarUsuarios();
            assertNotNull(usuarios, "La lista de usuarios no debería ser nula.");
        });
    }
    @Test
    void iniciarSesionTest() {

        LoginDTO loginDTO = new LoginDTO(
                "barbelaezaguirre@gmail.com",
                "pxWsvy"
        );

        // Se espera que no se lance ninguna excepción
        assertDoesNotThrow(() -> {
            // Se inicia sesion y retorna el token JWS
            TokenDTO tokenDTO = usuarioServicio.iniciarSesion(loginDTO);
            System.out.println(tokenDTO.token());
            System.out.println(jwtUtils.parseJwt(tokenDTO.token()).getPayload().toString());
            // Se espera que el valor del token no sea nulo
            assertNotNull(tokenDTO);
        });

    }
}