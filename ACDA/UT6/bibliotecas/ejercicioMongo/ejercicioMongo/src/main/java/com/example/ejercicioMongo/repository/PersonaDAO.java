package com.example.ejercicioMongo.repository;

import com.example.ejercicioMongo.model.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PersonaDAO extends MongoRepository<Persona, String> {

    @Query("{ 'edad': { $gte: ?0 } }")
    List<Persona> findByEdadMin(int edadMin);

    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    List<Persona> findByNombreContainsIgnoreCase(String nombre);

    @Query("{ 'apps.nombre': { $regex: ?0, $options: 'i' } }")
    List<Persona> findByAppNombre(String nombreApp);

    @Query("{ 'apps.descripcion': { $regex: ?0, $options: 'i' } }")
    List<Persona> findByAppDescripcion(String appDescripcion);
}
