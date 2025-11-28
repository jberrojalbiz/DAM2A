package es.masanz.acda.ut4.crud_completo.db.service;

import es.masanz.acda.ut4.crud_completo.db.dao.PersonaDAO;
import es.masanz.acda.ut4.crud_completo.db.model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaDAO personaDAO;

    public Optional<Persona> findById(Integer personaId){
        return personaDAO.findById(personaId);
    }

    public Persona save(Persona persona) {
        return personaDAO.save(persona);
    }

    public Iterable<Persona> findAll() {
        return personaDAO.findAll();
    }

    public void deleteById(Integer personaID) {
        personaDAO.deleteById(personaID);
    }
}
