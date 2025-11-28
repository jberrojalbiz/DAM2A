package es.masanz.acda.ut4.crud_completo.db.service;

import es.masanz.acda.ut4.crud_completo.db.dao.DepartamentosDAO;
import es.masanz.acda.ut4.crud_completo.db.dao.PersonaDAO;
import es.masanz.acda.ut4.crud_completo.db.model.Departamentos;
import es.masanz.acda.ut4.crud_completo.db.model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartamentosService {


    @Autowired
    private DepartamentosDAO departamentosDAO;

    public Optional<Departamentos> findById(Integer departamentoId){
        return departamentosDAO.findById(departamentoId);
    }

    public Departamentos save(Departamentos departamentos) {
        return departamentosDAO.save(departamentos);
    }

    public Iterable<Departamentos> findAll() {
        return departamentosDAO.findAll();
    }

    public void deleteById(Integer departamentoID) {
        departamentosDAO.deleteById(departamentoID);
    }

}
