package com.example.ejercicioBiblioteca.repository;

import com.example.ejercicioBiblioteca.model.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibroRepository extends MongoRepository<Libro, String> {
}
