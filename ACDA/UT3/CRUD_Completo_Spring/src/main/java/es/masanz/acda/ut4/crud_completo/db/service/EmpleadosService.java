package es.masanz.acda.ut4.crud_completo.db.service;


import es.masanz.acda.ut4.crud_completo.db.dao.DepartamentosDAO;
import es.masanz.acda.ut4.crud_completo.db.dao.EmpleadosDAO;
import es.masanz.acda.ut4.crud_completo.db.model.Departamentos;
import es.masanz.acda.ut4.crud_completo.db.model.Empleados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpleadosService {

    @Autowired
    private EmpleadosDAO empleadosDAO;

    public Optional<Empleados> findById(Integer empleadoId){
        return empleadosDAO.findById(empleadoId);
    }

    public Empleados save(Empleados empleados) {
        return  empleadosDAO.save(empleados);
    }

    public Iterable<Empleados> findAll() {
        return empleadosDAO.findAll();
    }

    public void deleteById(Integer empleadoId) {
        empleadosDAO.deleteById(empleadoId);
    }
}
