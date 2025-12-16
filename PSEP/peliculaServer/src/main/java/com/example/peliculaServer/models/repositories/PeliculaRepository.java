package com.example.peliculaServer.models.repositories;

import com.example.peliculaServer.models.entities.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
}
