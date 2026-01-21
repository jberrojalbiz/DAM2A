package com.example.psep_ut4_api_server.server.review.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReviewRequest(
        @NotBlank @Size(max=80) String autor,
        @NotNull @DecimalMin("0.0") @DecimalMax("10.0") BigDecimal puntuacion,
        @NotBlank @Size(max=400) String comentario,
        @NotNull LocalDateTime fecha
) {}
