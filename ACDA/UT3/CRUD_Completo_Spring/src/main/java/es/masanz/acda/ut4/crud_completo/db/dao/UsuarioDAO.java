package es.masanz.acda.ut4.crud_completo.db.dao;

import es.masanz.acda.ut4.crud_completo.db.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioDAO extends CrudRepository<Usuario, Integer> {
    List<Usuario> findByCanalYoutubeIsNull();
}
