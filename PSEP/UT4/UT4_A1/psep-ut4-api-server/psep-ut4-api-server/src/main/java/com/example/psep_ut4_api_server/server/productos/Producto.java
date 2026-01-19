package com.example.psep_ut4_api_server.server.productos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Entidad JPA que representa un producto en la base de datos.
 * <p>
 * Cumple los tipos requeridos por la actividad: texto, entero, decimal y fecha.
 * La clave primaria se genera automáticamente.
 * </p>
 */
@Entity
@Table(name = "productos")
public class Producto {

    /** Identificador autogenerado. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del producto (texto). */
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
    @Column(nullable = false, length = 120)
    private String nombre;

    /** Stock disponible (entero). */
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    /** Precio del producto (decimal). */
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    @Column(nullable = false, precision = 10, scale = 2)
    private Double precio;

    /** Fecha de alta (fecha). */
    @NotNull(message = "La fecha de alta es obligatoria")
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    public Producto() {}

    // Getters y setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getStock() { return stock; }

    public void setStock(Integer stock) { this.stock = stock; }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }

    public LocalDate getFechaAlta() { return fechaAlta; }

    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
}
