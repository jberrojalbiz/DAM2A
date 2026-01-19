package com.example.psep_ut4_api_server.client.service;

import com.example.psep_ut4_api_server.client.dto.ProductoDto;
import com.example.psep_ut4_api_server.client.http.ProductoApiClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Capa de servicio del cliente para encapsular las llamadas HTTP.
 */
@Service
public class ProductoService {

    private final ProductoApiClient api;

    public ProductoService(ProductoApiClient api) {
        this.api = api;
    }

    public List<ProductoDto> getAll() { return api.getAll(); }

    public ProductoDto getById(Long id) { return api.getById(id); }

    public ProductoDto create(ProductoDto p) { return api.create(p); }

    public ProductoDto update(Long id, ProductoDto p) { return api.update(id, p); }

    public void delete(Long id) { api.delete(id); }
}
