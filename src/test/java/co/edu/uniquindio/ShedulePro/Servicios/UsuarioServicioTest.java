package co.edu.uniquindio.ShedulePro.Servicios;

import co.edu.uniquindio.ShedulePro.Config.JWTUtils;
import co.edu.uniquindio.ShedulePro.dto.usuario.LoginDTO;
import co.edu.uniquindio.ShedulePro.dto.jws.TokenDTO;
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
                "1006535766",
                "Brahian Andres",
                "Arbelae Aguirre",
                "3147408976",
                "barbelaezaguirre@gmail.com",
                Departamento.RECURSOS_HUMANOS,
                Cargo.EMPLEADO,
                LocalDateTime.of(2025, 1, 1, 10, 0),
                7000
        );

        assertDoesNotThrow(() -> {
            String id = usuarioServicio.crearUsuario(crearUsuarioDTO);
            assertNotNull(id, "El ID del usuario no debería ser nulo.");
        });
    }

    @Test
    void editarUsuarioTest() {
        EditarUsuarioDTO editarUsuarioDTO = new EditarUsuarioDTO(
                "68324fe4b115e03a67d67ece",
                "1006535766",
                "Brahian Andres",
                "Arbelae Aguirre",
                "3147408976",
                "barbelaezaguirre@gmail.com",
                Departamento.ADMINISTRACION_Y_FINANZAS,
                Cargo.EMPLEADO,
                LocalDateTime.of(2025, 1, 1, 10, 0),
                8000
        );

        assertDoesNotThrow(() -> {
            String id = usuarioServicio.editarUsuario(editarUsuarioDTO);
            assertNotNull(id, "El ID del usuario no debería ser nulo.");
        });
    }

    @Test
    void eliminarUsuarioTest() {
        String id = "68324fe4b115e03a67d67ece";

        assertDoesNotThrow(() -> {
            String respuesta = usuarioServicio.eliminarUsuario(id);
            assertNotNull(respuesta, "La respuesta no debería ser nula.");
        });
    }

    @Test
    void obtenerUsuarioTest() {
        String id = "68324fe4b115e03a67d67ece";

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
                "jhogillds@gmail.com",
                "ADRyOj"
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