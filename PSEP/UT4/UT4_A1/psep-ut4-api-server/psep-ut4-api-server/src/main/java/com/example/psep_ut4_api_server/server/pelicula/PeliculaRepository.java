package com.example.psep_ut4_api_server.server.pelicula;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    List<Pelicula> findByTituloContainingIgnoreCase(String titulo);

    List<Pelicula> findByGeneroIgnoreCase(String genero);

    List<Pelicula> findByDirectorContainingIgnoreCase(String director);

    List<Pelicula> findByGeneroIgnoreCaseAndTituloContainingIgnoreCase(String genero, String titulo);
}
