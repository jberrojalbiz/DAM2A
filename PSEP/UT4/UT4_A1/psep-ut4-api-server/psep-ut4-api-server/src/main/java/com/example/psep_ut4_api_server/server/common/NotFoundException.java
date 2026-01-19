package com.example.psep_ut4_api_server.server.common;

/**
 * Excepción usada cuando un recurso no existe.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Crea una excepción de recurso no encontrado.
     *
     * @param message detalle del error.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
