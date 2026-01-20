package com.example.psep_ut4_api_server.server.pelicula.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PeliculaResponse(
        Long id,
        String titulo,
        String director,
        Integer duracion,
        BigDecimal valoracion,
        LocalDate fechaEstreno,
        String genero
) {}
