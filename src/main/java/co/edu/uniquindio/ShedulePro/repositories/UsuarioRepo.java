package co.edu.uniquindio.ShedulePro.repositories;

import co.edu.uniquindio.ShedulePro.model.documents.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepo extends MongoRepository<Usuario, String> {

    boolean existsByCedula(String cedula);

    boolean existsByEmail(String email);
}