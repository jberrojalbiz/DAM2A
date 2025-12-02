package es.masanz.acda.ut4.crud_completo.db.service;

import es.masanz.acda.ut4.crud_completo.db.dao.UsuarioDAO;
import es.masanz.acda.ut4.crud_completo.db.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioDAO usuarioDAO;

    public Optional<Usuario> findById(Integer usuarioId){
        return usuarioDAO.findById(usuarioId);
    }

    public Iterable<Usuario> findAll() {
        return usuarioDAO.findAll();
    }

    public void deleteById(Integer usuarioId) {
        usuarioDAO.deleteById(usuarioId);
    }

    public Usuario save(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if (!usuario.getEmail().matches("^[^@]+@[^@]+\\.[^@]+$")) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }

        if (usuario.getPassword() == null || usuario.getPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }

        return usuarioDAO.save(usuario);
    }

    public Iterable<Usuario> findUsuariosSinCanal() {
        return usuarioDAO.findByCanalYoutubeIsNull();
    }

}
