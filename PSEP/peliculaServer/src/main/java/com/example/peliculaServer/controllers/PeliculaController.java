package com.example.peliculaServer.controllers;

import com.example.peliculaServer.models.entities.Pelicula;
import com.example.peliculaServer.models.services.PeliculasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
    private final PeliculasService peliculasService;

    public PeliculaController(PeliculasService peliculasService) {
        this.peliculasService = peliculasService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<Pelicula>> todos() {
        List<Pelicula> peliculas = peliculasService.todos();
        return ResponseEntity.ok(peliculas);
    }
}
