package com.example.psep_ut4_api_server.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReviewDto(
        Long id,
        String autor,
        BigDecimal puntuacion,
        String comentario,
        LocalDateTime fecha
) {}
