package com.example.psep_ut4_api_server.server.productos;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST de productos.
 * <p>
 * Expone un CRUD completo con respuestas HTTP bien formadas y códigos adecuados.
 * </p>
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /**
     * GET /api/productos
     *
     * @return lista de productos (200 OK).
     */
    @GetMapping
    public ResponseEntity<List<Producto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * GET /api/productos/{id}
     *
     * @param id identificador.
     * @return producto (200 OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * POST /api/productos
     *
     * @param producto producto a crear (validado).
     * @return producto creado (201 Created) + Location.
     */
    @PostMapping
    public ResponseEntity<Producto> create(@Valid @RequestBody Producto producto) {
        Producto created = service.create(producto);
        return ResponseEntity.created(URI.create("/api/productos/" + created.getId()))
                .body(created);
    }

    /**
     * PUT /api/productos/{id}
     *
     * @param id identificador.
     * @param producto datos nuevos (validado).
     * @return producto actualizado (200 OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(service.update(id, producto));
    }

    /**
     * DELETE /api/productos/{id}
     *
     * @param id identificador.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
