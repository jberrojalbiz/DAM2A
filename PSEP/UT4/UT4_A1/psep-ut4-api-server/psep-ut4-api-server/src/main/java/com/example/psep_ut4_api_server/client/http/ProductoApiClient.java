package com.example.psep_ut4_api_server.client.http;

import com.example.psep_ut4_api_server.client.dto.ProductoDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;

import java.util.List;

/**
 * Cliente declarativo para interactuar con la API REST.
 */
@HttpExchange("/api/productos")
public interface ProductoApiClient {

    /** Obtiene todos los productos. */
    @GetExchange
    List<ProductoDto> getAll();

    /** Obtiene un producto por id. */
    @GetExchange("/{id}")
    ProductoDto getById(@PathVariable Long id);

    /** Crea un producto. */
    @PostExchange
    ProductoDto create(@RequestBody ProductoDto producto);

    /** Actualiza un producto. */
    @PutExchange("/{id}")
    ProductoDto update(@PathVariable Long id, @RequestBody ProductoDto producto);

    /** Elimina un producto. */
    @DeleteExchange("/{id}")
    void delete(@PathVariable Long id);
}
