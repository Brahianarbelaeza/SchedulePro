package co.edu.uniquindio.ShedulePro.services.implementaciones;

import co.edu.uniquindio.ShedulePro.dto.usuario.CrearUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.EditarUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.InformacionUsuarioDTO;
import co.edu.uniquindio.ShedulePro.dto.usuario.ItemUsuarioDTO;
import co.edu.uniquindio.ShedulePro.model.documents.Usuario;
import co.edu.uniquindio.ShedulePro.repositories.UsuarioRepo;
import co.edu.uniquindio.ShedulePro.services.interfaces.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;


    @Service
    public class UsuarioServicioImpl implements UsuarioServicio {

        @Autowired
        private UsuarioRepo usuarioRepo;

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
                    obtenerContrasena(),
                    dto.departamento(),
                    dto.cargo(),
                    dto.fechaContratacion()
            );
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
            if (!usuarioRepo.existsById(id)) {
                throw new Exception("El usuario no existe.");
            }
            usuarioRepo.deleteById(id);
            return "Usuario eliminado con éxito";
        }

        @Override
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
        public List<ItemUsuarioDTO> listarUsuarios() throws Exception {
            return usuarioRepo.findAll().stream()
                    .map(usuario -> new ItemUsuarioDTO(
                            usuario.getId(),
                            usuario.getNombre(),
                            usuario.getApellido(),
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
}
