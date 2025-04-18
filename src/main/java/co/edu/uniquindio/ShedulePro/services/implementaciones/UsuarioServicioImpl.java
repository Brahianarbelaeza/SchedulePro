package co.edu.uniquindio.ShedulePro.services.implementaciones;

import co.edu.uniquindio.ShedulePro.Config.JWTUtils;
import co.edu.uniquindio.ShedulePro.dto.usuario.LoginDTO;
import co.edu.uniquindio.ShedulePro.dto.jws.TokenDTO;
import co.edu.uniquindio.ShedulePro.dto.email.EmailDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.CrearUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.EditarUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.InformacionUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.ItemUsuarioDTO;
import co.edu.uniquindio.ShedulePro.model.documents.Usuario;
import co.edu.uniquindio.ShedulePro.model.enums.Cargo;
import co.edu.uniquindio.ShedulePro.model.enums.Estado;
import co.edu.uniquindio.ShedulePro.repositories.UsuarioRepo;
import co.edu.uniquindio.ShedulePro.services.interfaces.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UsuarioServicioImpl implements UsuarioServicio {


    private final UsuarioRepo usuarioRepo;
    private final EmailServicioImpl emailServicio;
    private final JWTUtils jwtUtils;

    @Override
    public String crearUsuario(CrearUsuarioDTO dto) throws Exception {
        if (usuarioRepo.existsByCedula(dto.cedula())) {
            throw new Exception("La cédula ya está registrada.");
        }
        if (usuarioRepo.existsByEmail(dto.email())) {
            throw new Exception("El correo electrónico ya está registrado.");
        }
        Usuario usuario = new Usuario(
                dto.cedula(),
                dto.nombre(),
                dto.apellido(),
                dto.telefono(),
                dto.email(),
                dto.departamento(),
                dto.cargo(),
                dto.fechaContratacion()
        );
        usuario.setEstado(Estado.ACTIVO);
        String contrasena = obtenerContrasena();
        usuario.setPassword(encriptarPassword(contrasena));
        emailServicio.enviarCorreo(new EmailDTO(
                "Schedule Pro - Registro Exitoso",
                "<html>" +
                        "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>" +
                        "<div style='max-width: 500px; margin: auto; background: #ffffff; padding: 20px; " +
                        "border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);'>" +
                        "<h2 style='color: #333; text-align: center;'>Bienvenido a Schedule Pro</h2>" +
                        "<p style='color: #555; font-size: 16px; line-height: 1.5;'>" +
                        "¡Hola! Se informa que usted ha sido registrado en la aplicación <strong>Schedule Pro</strong>. " +
                        "A partir de ahora, podrá iniciar sesión y consultar sus horarios con las siguientes credenciales:" +
                        "</p>" +
                        "<div style='background: #f8f8f8; padding: 10px; border-radius: 5px; margin: 20px 0;'>" +
                        "<p style='margin: 5px 0;'><strong>Usuario:</strong> <span style='color: #007bff;'>" + usuario.getEmail() + "</span></p>" +
                        "<p style='margin: 5px 0;'><strong>Contraseña:</strong> <span style='color: #28a745;'>" + contrasena + "</span></p>" +
                        "</div>" +
                        "<p style='text-align: center; margin-top: 20px;'>" +
                        "<a href='https://schedulepro.com/login' style='display: inline-block; padding: 10px 20px; " +
                        "color: white; background: #007bff; text-decoration: none; border-radius: 5px;'>Iniciar Sesión</a>" +
                        "</p>" +
                        "<p style='color: #777; font-size: 14px; text-align: center; margin-top: 20px;'>" +
                        "Si no solicitó este registro, ignore este mensaje." +
                        "</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>",
                usuario.getEmail()
        ));
        usuarioRepo.save(usuario);
        return usuario.getId();
    }



    @Override
    public String editarUsuario(EditarUsuarioDTO dto) throws Exception {
        Usuario usuario = usuarioRepo.findById(dto.id())
                .orElseThrow(() -> new Exception("Usuario no encontrado."));

        if (!usuario.getCedula().equals(dto.cedula()) && usuarioRepo.existsByCedula(dto.cedula())) {
            throw new Exception("La cédula ya está registrada.");
        }
        if (!usuario.getEmail().equals(dto.email()) && usuarioRepo.existsByEmail(dto.email())) {
            throw new Exception("El correo electrónico ya está registrado.");
        }

        usuario.setCedula(dto.cedula());
        usuario.setNombre(dto.nombre());
        usuario.setApellido(dto.apellido());
        usuario.setTelefono(dto.telefono());
        usuario.setEmail(dto.email());
        usuario.setDepartamento(dto.departamento());
        usuario.setCargo(dto.cargo());
        usuario.setFechaContratacion(dto.fechaContratacion());

        usuarioRepo.save(usuario);
        return usuario.getId();
    }

    @Override
    public String eliminarUsuario(String id) throws Exception {
        Usuario usuario = obtenerPorId(id);
        usuario.setEstado(Estado.INACTIVO);
        usuarioRepo.save(usuario);
        return "usuario eliminado correctamente.";
    }

    @Override
    @Transactional(readOnly = true)//Este metodo solo va a utilizar recurso para lectura
    public InformacionUsuarioDTO obtenerUsuario(String id) throws Exception {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado."));

        return new InformacionUsuarioDTO(
                usuario.getId(),
                usuario.getCedula(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getTelefono(),
                usuario.getEmail(),
                usuario.getDepartamento(),
                usuario.getCargo(),
                usuario.getFechaContratacion()
        );
    }

    @Override
    public List<ItemUsuarioDTO> listarUsuarios() {
        return usuarioRepo.findAll().stream()
                .filter(usuario -> usuario.getCargo().equals(Cargo.EMPLEADO )&& usuario.getEstado() == Estado.ACTIVO)
                .map(usuario -> new ItemUsuarioDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getTelefono(),
                        usuario.getEmail(),
                        usuario.getDepartamento(),
                        usuario.getCargo()))
                .collect(Collectors.toList());
    }

    private String obtenerContrasena() {
        String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder codigo = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int indiceAleatorio = random.nextInt(CARACTERES.length());
            codigo.append(CARACTERES.charAt(indiceAleatorio));

        }
        return codigo.toString();
    }
    private String encriptarPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode( password );
    }
    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {


        Usuario usuario = obtenerPorEmail(loginDTO.email());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if( !passwordEncoder.matches(loginDTO.password(), usuario.getPassword()) ) {
            throw new Exception("La contraseña es incorrecta");
        }


        Map<String, Object> map = construirClaims(usuario);
        return new TokenDTO( jwtUtils.generarToken(usuario.getEmail(), map) );
    }
    private Usuario obtenerPorEmail(String correo) throws Exception {

        Optional<Usuario> usuarioOptional = usuarioRepo.buscarEmail(correo);

        if (usuarioOptional.isEmpty()){
            throw new Exception("El correo dado no está registrado");
        }

        return usuarioOptional.get();
    }
    private Map<String, Object> construirClaims(Usuario usuario) {
        return Map.of(
                "rol", usuario.getCargo(),
                "nombre", usuario.getNombre(),
                "id", usuario.getId()
        );
    }

    private  Usuario obtenerPorId(String id) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepo.buscarPorId(id);
        if (usuarioOptional.isEmpty()){
            throw new Exception("El usuario no existe.");
        }
        return usuarioOptional.get();
    }


}
