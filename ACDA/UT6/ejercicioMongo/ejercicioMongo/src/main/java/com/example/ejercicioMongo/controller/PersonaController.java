package com.example.ejercicioMongo.controller;

import com.example.ejercicioMongo.model.App;
import com.example.ejercicioMongo.model.Persona;
import com.example.ejercicioMongo.repository.PersonaDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaDAO personaDAO;

    public PersonaController(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    @GetMapping
    public ResponseEntity<List<Persona>> getAll() {
        return ResponseEntity.ok(personaDAO.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<Persona> p = personaDAO.findById(id);
        if (p.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada con id: " + id);
        }
        return ResponseEntity.ok(p.get());
    }

    @PostMapping
    public ResponseEntity<Persona> create(@RequestBody Persona persona) {
        Persona saved = personaDAO.save(new Persona(persona.getNombre(), persona.getEdad()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!personaDAO.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede borrar. Persona no encontrada con id: " + id);
        }
        personaDAO.deleteById(id);
        return ResponseEntity.ok("Persona eliminada con id: " + id);
    }

    @PostMapping("/{id}/apps")
    public ResponseEntity<?> addApp(@PathVariable String id, @RequestBody App app) {
        Optional<Persona> pOpt = personaDAO.findById(id);
        if (pOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada con id: " + id);
        }

        if (app.getNombre() == null || app.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre de la app es obligatorio");
        }

        Persona persona = pOpt.get();

        boolean exists = persona.getApps().stream()
                .anyMatch(a -> a.getNombre() != null && a.getNombre().equalsIgnoreCase(app.getNombre()));

        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe una app con nombre: " + app.getNombre());
        }

        persona.getApps().add(app);
        Persona updated = personaDAO.save(persona);

        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @GetMapping("/{id}/apps")
    public ResponseEntity<?> getApps(@PathVariable String id) {
        Optional<Persona> pOpt = personaDAO.findById(id);
        if (pOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada con id: " + id);
        }
        return ResponseEntity.ok(pOpt.get().getApps());
    }

    @DeleteMapping("/{id}/apps/{appNombre}")
    public ResponseEntity<?> deleteApp(@PathVariable String id, @PathVariable String appNombre) {
        Optional<Persona> pOpt = personaDAO.findById(id);
        if (pOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada con id: " + id);
        }

        Persona persona = pOpt.get();

        boolean removed = persona.getApps().removeIf(a ->
                a.getNombre() != null && a.getNombre().equalsIgnoreCase(appNombre)
        );

        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La persona no tiene una app llamada: " + appNombre);
        }

        Persona updated = personaDAO.save(persona);
        return ResponseEntity.ok(updated);
    }

    // ---------------------------------------
// ✅ CONSULTAS PERSONALIZADAS (UT6_A4)
// ---------------------------------------

    // 1) GET /api/personas/filtro/edad/{edadMin}
    @GetMapping("/filtro/edad/{edadMin}")
    public ResponseEntity<List<Persona>> filtroEdad(@PathVariable int edadMin) {
        return ResponseEntity.ok(personaDAO.findByEdadMin(edadMin));
    }

    // 2) GET /api/personas/filtro/nombre/{nombre}
// Busca por nombre que contenga el texto (sin importar mayúsculas/minúsculas)
    @GetMapping("/filtro/nombre/{nombre}")
    public ResponseEntity<List<Persona>> filtroNombre(@PathVariable String nombre) {
        // Para "contiene", construimos regex con .*texto.*
        String regex = ".*" + java.util.regex.Pattern.quote(nombre) + ".*";
        return ResponseEntity.ok(personaDAO.findByNombreContainsIgnoreCase(regex));
    }

    // 3) GET /api/personas/filtro/app/{nombreApp}
// Personas que tengan una app instalada con ese nombre
    @GetMapping("/filtro/app/{nombreApp}")
    public ResponseEntity<List<Persona>> filtroAppNombre(@PathVariable String nombreApp) {
        // También hacemos "contiene" por si ponen parte del nombre
        String regex = ".*" + java.util.regex.Pattern.quote(nombreApp) + ".*";
        return ResponseEntity.ok(personaDAO.findByAppNombre(regex));
    }

    // 4) GET /api/personas/filtro/appDescripcion/{appDescripcion}
// Personas con alguna app cuya descripción contenga esa palabra clave
    @GetMapping("/filtro/appDescripcion/{appDescripcion}")
    public ResponseEntity<List<Persona>> filtroAppDescripcion(@PathVariable String appDescripcion) {
        String regex = ".*" + java.util.regex.Pattern.quote(appDescripcion) + ".*";
        return ResponseEntity.ok(personaDAO.findByAppDescripcion(regex));
    }

}
