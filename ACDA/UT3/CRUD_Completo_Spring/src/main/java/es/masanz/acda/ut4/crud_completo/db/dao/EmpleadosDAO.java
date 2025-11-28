package es.masanz.acda.ut4.crud_completo.db.dao;

import es.masanz.acda.ut4.crud_completo.db.model.Departamentos;
import es.masanz.acda.ut4.crud_completo.db.model.Empleados;
import org.springframework.data.repository.CrudRepository;

public interface EmpleadosDAO extends CrudRepository<Empleados, Integer> {
}
