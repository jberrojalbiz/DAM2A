package com.example.ejercicioBiblioteca.controller;

import com.example.ejercicioBiblioteca.model.Biblioteca;
import com.example.ejercicioBiblioteca.model.Libro;
import com.example.ejercicioBiblioteca.repository.BibliotecaRepository;
import com.example.ejercicioBiblioteca.repository.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bibliotecas")
public class BibliotecaController {

    private final BibliotecaRepository bibliotecaRepo;
    private final LibroRepository libroRepo;

    public BibliotecaController(BibliotecaRepository bibliotecaRepo, LibroRepository libroRepo) {
        this.bibliotecaRepo = bibliotecaRepo;
        this.libroRepo = libroRepo;
    }

    // -------------------------
    // CRUD BIBLIOTECAS
    // -------------------------

    // GET /api/bibliotecas
    @GetMapping
    public ResponseEntity<List<Biblioteca>> getAll() {
        return ResponseEntity.ok(bibliotecaRepo.findAll());
    }

    // GET /api/bibliotecas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<Biblioteca> b = bibliotecaRepo.findById(id);
        if (b.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Biblioteca no encontrada con id: " + id);
        }
        return ResponseEntity.ok(b.get());
    }

    // POST /api/bibliotecas
    @PostMapping
    public ResponseEntity<Biblioteca> create(@RequestBody Biblioteca b) {
        Biblioteca nueva = new Biblioteca(b.getNombre(), b.getUbicacion());
        Biblioteca saved = bibliotecaRepo.save(nueva);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // DELETE /api/bibliotecas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!bibliotecaRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede borrar. Biblioteca no encontrada con id: " + id);
        }
        bibliotecaRepo.deleteById(id);
        return ResponseEntity.ok("Biblioteca eliminada con id: " + id);
    }

    // -------------------------
    // LIBROS DENTRO DE BIBLIOTECA (REFERENCIAS @DBRef)
    // -------------------------

    // POST /api/bibliotecas/{id}/libros
    @PostMapping("/{id}/libros")
    public ResponseEntity<?> addLibro(@PathVariable String id, @RequestBody Libro libro) {
        Optional<Biblioteca> bOpt = bibliotecaRepo.findById(id);
        if (bOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Biblioteca no encontrada con id: " + id);
        }

        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            return ResponseEntity.badRequest().body("El título del libro es obligatorio");
        }
        if (libro.getAutor() == null || libro.getAutor().isBlank()) {
            return ResponseEntity.badRequest().body("El autor del libro es obligatorio");
        }

        Biblioteca b = bOpt.get();

        // Evitar duplicados por título en esa biblioteca
        boolean exists = b.getLibros().stream()
                .anyMatch(l -> l.getTitulo() != null && l.getTitulo().equalsIgnoreCase(libro.getTitulo()));

        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un libro con ese título en esta biblioteca: " + libro.getTitulo());
        }

        // Guardamos el libro en su colección
        Libro savedLibro = libroRepo.save(new Libro(libro.getTitulo(), libro.getAutor()));

        // Referenciamos el libro en la biblioteca
        b.getLibros().add(savedLibro);
        Biblioteca updated = bibliotecaRepo.save(b);

        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    // DELETE /api/bibliotecas/{id}/libros/{titulo}
    @DeleteMapping("/{id}/libros/{titulo}")
    public ResponseEntity<?> deleteLibro(@PathVariable String id, @PathVariable String titulo) {
        Optional<Biblioteca> bOpt = bibliotecaRepo.findById(id);
        if (bOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Biblioteca no encontrada con id: " + id);
        }

        Biblioteca b = bOpt.get();

        Libro encontrado = b.getLibros().stream()
                .filter(l -> l.getTitulo() != null && l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);

        if (encontrado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un libro con título '" + titulo + "' en esta biblioteca");
        }

        // Quitar referencia
        b.getLibros().removeIf(l -> l.getId() != null && l.getId().equals(encontrado.getId()));
        Biblioteca updated = bibliotecaRepo.save(b);

        // Borrar libro de la colección (si tu enunciado lo quiere así)
        libroRepo.deleteById(encontrado.getId());

        return ResponseEntity.ok(updated);
    }
}
