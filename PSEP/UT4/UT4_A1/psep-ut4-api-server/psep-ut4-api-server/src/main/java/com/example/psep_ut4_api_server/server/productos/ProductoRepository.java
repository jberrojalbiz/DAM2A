package com.example.psep_ut4_api_server.server.productos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad {@link Producto}.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
