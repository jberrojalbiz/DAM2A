package com.example.psep_ut4_api_server.server.review.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        String autor,
        BigDecimal puntuacion,
        String comentario,
        LocalDateTime fecha
) {}
