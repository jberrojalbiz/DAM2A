package es.masanz.acda.ut4.crud_completo.db.dao;

import es.masanz.acda.ut4.crud_completo.db.model.Departamentos;
import es.masanz.acda.ut4.crud_completo.db.model.Persona;
import org.springframework.data.repository.CrudRepository;

public interface DepartamentosDAO extends CrudRepository<Departamentos, Integer> {
}
