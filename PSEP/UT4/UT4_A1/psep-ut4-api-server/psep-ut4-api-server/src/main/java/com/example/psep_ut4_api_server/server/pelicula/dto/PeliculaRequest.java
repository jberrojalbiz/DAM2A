package com.example.psep_ut4_api_server.server.pelicula.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PeliculaRequest(
        @NotBlank @Size(max = 150) String titulo,
        @NotBlank @Size(max = 100) String director,
        @NotNull @Min(1) Integer duracion,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) @DecimalMax(value = "10.0", inclusive = true) BigDecimal valoracion,
        @NotNull LocalDate fechaEstreno,
        @NotBlank @Size(max = 50) String genero
) {}
