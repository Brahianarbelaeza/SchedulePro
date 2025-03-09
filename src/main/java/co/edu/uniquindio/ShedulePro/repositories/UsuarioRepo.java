package co.edu.uniquindio.ShedulePro.repositories;

import co.edu.uniquindio.ShedulePro.model.documents.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends MongoRepository<Usuario, String> {

    boolean existsByCedula(String cedula);

    boolean existsByEmail(String email);

    @Query("{email: ?0}")
    Optional<Usuario> buscarEmail(String email);

    @Query("{_id: ?0}")
    Optional<Usuario> buscarPorId(String id);


}