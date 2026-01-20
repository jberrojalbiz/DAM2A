package com.example.psep_ut4_api_server.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PeliculaDto(
        Long id,
        String titulo,
        String director,
        Integer duracion,
        BigDecimal valoracion,
        LocalDate fechaEstreno,
        String genero
) {}
