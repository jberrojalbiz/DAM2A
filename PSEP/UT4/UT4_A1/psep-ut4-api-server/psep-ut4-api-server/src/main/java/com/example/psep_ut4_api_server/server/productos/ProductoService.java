package com.example.psep_ut4_api_server.server.productos;

import com.example.psep_ut4_api_server.server.common.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Capa de servicio para la gestión de productos.
 * <p>
 * Separa la lógica de negocio del controlador y la persistencia.
 * </p>
 */
@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    /**
     * Obtiene todos los productos.
     *
     * @return lista de productos.
     */
    public List<Producto> findAll() {
        return repository.findAll();
    }

    /**
     * Obtiene un producto por id.
     *
     * @param id identificador.
     * @return producto encontrado.
     * @throws NotFoundException si no existe.
     */
    public Producto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto con id " + id + " no encontrado"));
    }

    /**
     * Crea un nuevo producto.
     *
     * @param producto producto a crear.
     * @return producto creado.
     */
    public Producto create(Producto producto) {
        producto.setId(null);
        return repository.save(producto);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id identificador del producto.
     * @param producto datos nuevos.
     * @return producto actualizado.
     * @throws NotFoundException si no existe.
     */
    public Producto update(Long id, Producto producto) {
        Producto existing = findById(id);

        existing.setNombre(producto.getNombre());
        existing.setStock(producto.getStock());
        existing.setPrecio(producto.getPrecio());
        existing.setFechaAlta(producto.getFechaAlta());

        return repository.save(existing);
    }

    /**
     * Elimina un producto por id.
     *
     * @param id identificador.
     * @throws NotFoundException si no existe.
     */
    public void delete(Long id) {
        Producto existing = findById(id);
        repository.delete(existing);
    }
}