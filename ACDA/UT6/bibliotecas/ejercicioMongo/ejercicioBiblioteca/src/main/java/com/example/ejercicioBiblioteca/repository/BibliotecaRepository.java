package com.example.ejercicioBiblioteca.repository;

import com.example.ejercicioBiblioteca.model.Biblioteca;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BibliotecaRepository extends MongoRepository<Biblioteca, String> {
}
