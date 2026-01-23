package com.example.ejercicioMongo.repository;

import com.example.ejercicioMongo.model.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PersonaDAO extends MongoRepository<Persona, String> {

    // 1) Personas con edad >= edadMin
    @Query("{ 'edad': { $gte: ?0 } }")
    List<Persona> findByEdadMin(int edadMin);

    // 2) Nombre contiene texto (case-insensitive)
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    List<Persona> findByNombreContainsIgnoreCase(String nombre);

    // 3) Personas que tengan instalada una app con nombreApp
    @Query("{ 'apps.nombre': { $regex: ?0, $options: 'i' } }")
    List<Persona> findByAppNombre(String nombreApp);

    // 4) Personas que tengan al menos una app cuya descripción contenga palabra clave (case-insensitive)
    @Query("{ 'apps.descripcion': { $regex: ?0, $options: 'i' } }")
    List<Persona> findByAppDescripcion(String appDescripcion);
}
