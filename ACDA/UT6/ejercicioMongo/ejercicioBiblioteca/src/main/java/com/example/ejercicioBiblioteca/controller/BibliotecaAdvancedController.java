package com.example.ejercicioBiblioteca.controller;

import com.example.ejercicioBiblioteca.dto.AutorTopDTO;
import com.example.ejercicioBiblioteca.dto.BibliotecaConteoDTO;
import com.example.ejercicioBiblioteca.model.Biblioteca;
import com.example.ejercicioBiblioteca.model.Libro;
import com.example.ejercicioBiblioteca.service.BibliotecaAdvancedService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class BibliotecaAdvancedController {

    private final BibliotecaAdvancedService service;

    public BibliotecaAdvancedController(BibliotecaAdvancedService service) {
        this.service = service;
    }

    // -------------------------
    // PARTE 1: AGREGACIONES
    // -------------------------

    // GET /api/bibliotecas/libros/conteo
    @GetMapping("/api/bibliotecas/libros/conteo")
    public ResponseEntity<List<BibliotecaConteoDTO>> conteo() {
        return ResponseEntity.ok(service.conteoLibrosPorBiblioteca());
    }

    // GET /api/autores/top
    @GetMapping("/api/autores/top")
    public ResponseEntity<List<AutorTopDTO>> topAutores() {
        return ResponseEntity.ok(service.top5Autores());
    }

    // GET /api/bibliotecas/libros/mayor/{cantidad}
    @GetMapping("/api/bibliotecas/libros/mayor/{cantidad}")
    public ResponseEntity<List<BibliotecaConteoDTO>> mayor(@PathVariable int cantidad) {
        return ResponseEntity.ok(service.bibliotecasConMasDeXLibros(cantidad));
    }

    // -------------------------
    // PARTE 2: PAGINACIÓN
    // -------------------------

    // GET /api/bibliotecas/{id}/libros?page=0&size=5
    @GetMapping("/api/bibliotecas/{id}/libros")
    public ResponseEntity<?> librosPaginados(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        try {
            Page<Libro> result = service.librosPaginados(id, page, size);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // -------------------------
    // PARTE 3: TRANSACCIÓN
    // -------------------------

    // POST /api/bibliotecas/migrar-libros?origen=ID1&destino=ID2
    @PostMapping("/api/bibliotecas/migrar-libros")
    public ResponseEntity<?> migrar(
            @RequestParam String origen,
            @RequestParam String destino
    ) {
        try {
            Biblioteca updated = service.migrarLibros(origen, destino);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            // Conflicto por libro duplicado en destino
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en migración: " + e.getMessage());
        }
    }
}
