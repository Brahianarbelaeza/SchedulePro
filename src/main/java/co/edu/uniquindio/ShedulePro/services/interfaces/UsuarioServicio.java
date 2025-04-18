package co.edu.uniquindio.ShedulePro.services.interfaces;

import co.edu.uniquindio.ShedulePro.dto.usuario.LoginDTO;
import co.edu.uniquindio.ShedulePro.dto.jws.TokenDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.CrearUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.EditarUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.InformacionUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.ItemUsuarioDTO;

import java.util.List;

public interface UsuarioServicio {

    String crearUsuario(CrearUsuarioDTO crearUsuarioDTO) throws Exception;

    String editarUsuario(EditarUsuarioDTO editarUsuarioDTO) throws Exception;

    String eliminarUsuario(String id) throws Exception;

    InformacionUsuarioDTO obtenerUsuario(String id) throws Exception;

    List <ItemUsuarioDTO> listarUsuarios() throws Exception;

    TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception;
}
