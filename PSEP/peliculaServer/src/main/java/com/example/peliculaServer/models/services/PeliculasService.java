package com.example.peliculaServer.models.services;

import com.example.peliculaServer.models.entities.Pelicula;
import com.example.peliculaServer.models.repositories.PeliculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculasService {
    private final PeliculaRepository peliculaRepository;

    public PeliculasService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public List<Pelicula> todos() {
        return  peliculaRepository.findAll();
    }

    public Pelicula uno(Long id) {
        return peliculaRepository.findById(id).orElse(null);
    }

    public Pelicula guardar(Pelicula pelicula) {
        if (pelicula.getTitulo() == null) return null;
        return peliculaRepository.save(pelicula);
    }
}
