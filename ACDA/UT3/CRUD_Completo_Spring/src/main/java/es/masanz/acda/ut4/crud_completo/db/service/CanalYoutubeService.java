package es.masanz.acda.ut4.crud_completo.db.service;

import es.masanz.acda.ut4.crud_completo.db.dao.CanalYoutubeDAO;
import es.masanz.acda.ut4.crud_completo.db.model.CanalYoutube;
import es.masanz.acda.ut4.crud_completo.db.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CanalYoutubeService {
    @Autowired
    private CanalYoutubeDAO canalDAO;

    public Optional<CanalYoutube> findById(Integer canalId) {
        return canalDAO.findById(canalId);
    }

    public Iterable<CanalYoutube> findAll() {
        return canalDAO.findAll();
    }

    public void deleteById(Integer canalId) {
        canalDAO.deleteById(canalId);
    }

    public CanalYoutube save(CanalYoutube canal) {

        if (canal == null) {
            throw new IllegalArgumentException("El canal no puede ser null");
        }

        if (canal.getNombre() == null || canal.getNombre().length() < 3) {
            throw new IllegalArgumentException("El nombre del canal debe tener al menos 3 caracteres");
        }

        Usuario usuario = canal.getUsuario();
        if (usuario == null) {
            throw new IllegalArgumentException("El canal debe estar asociado a un usuario");
        }

        return canalDAO.save(canal);
    }
}
