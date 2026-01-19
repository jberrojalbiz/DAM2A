package com.example.psep_ut4_api_server.client.dto;

import java.time.LocalDate;

/**
 * DTO para transferir datos de Producto entre cliente y servidor.
 * <p>
 * Se recomienda usar records para DTOs por ser inmutables y simples.
 * </p>
 */
public record ProductoDto(
        Long id,
        String nombre,
        Integer stock,
        Double precio,
        LocalDate fechaAlta
) {}