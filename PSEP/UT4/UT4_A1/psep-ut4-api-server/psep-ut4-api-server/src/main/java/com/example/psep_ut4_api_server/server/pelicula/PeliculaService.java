package com.example.psep_ut4_api_server.server.pelicula;

import com.example.psep_ut4_api_server.server.common.ResourceNotFoundException;
import com.example.psep_ut4_api_server.server.pelicula.dto.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@Service
public class PeliculaService {

    private final PeliculaRepository repo;
    private static final Logger log = LoggerFactory.getLogger(PeliculaService.class);


    public PeliculaService(PeliculaRepository repo) {
        this.repo = repo;
    }

    public List<PeliculaResponse> getAll() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public PeliculaResponse getById(Long id) {
        Pelicula p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Película con id " + id + " no existe"));
        return toResponse(p);
    }

    public PeliculaResponse create(PeliculaRequest req) {
        log.info("CREAR película: titulo='{}', director='{}'", req.titulo(), req.director());
        Pelicula p = new Pelicula(
                null,
                req.titulo(),
                req.director(),
                req.duracion(),
                req.valoracion(),
                req.fechaEstreno(),
                req.genero()
        );
        return toResponse(repo.save(p));
    }

    public PeliculaResponse update(Long id, PeliculaRequest req) {
        log.info("ACTUALIZAR película id={} -> titulo='{}'", id, req.titulo());
        Pelicula p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Película con id " + id + " no existe"));

        p.setTitulo(req.titulo());
        p.setDirector(req.director());
        p.setDuracion(req.duracion());
        p.setValoracion(req.valoracion());
        p.setFechaEstreno(req.fechaEstreno());
        p.setGenero(req.genero());

        return toResponse(repo.save(p));
    }

    public void delete(Long id) {
        log.warn("BORRAR película id={}", id);
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Película con id " + id + " no existe");
        }
        repo.deleteById(id);
    }

    public List<PeliculaResponse> search(String titulo, String genero, String director) {

        List<Pelicula> result;

        boolean hasTitulo = titulo != null && !titulo.isBlank();
        boolean hasGenero = genero != null && !genero.isBlank();
        boolean hasDirector = director != null && !director.isBlank();

        if (hasGenero && hasTitulo && !hasDirector) {
            result = repo.findByGeneroIgnoreCaseAndTituloContainingIgnoreCase(genero, titulo);
        } else if (hasTitulo && !hasGenero && !hasDirector) {
            result = repo.findByTituloContainingIgnoreCase(titulo);
        } else if (hasGenero && !hasTitulo && !hasDirector) {
            result = repo.findByGeneroIgnoreCase(genero);
        } else if (hasDirector && !hasTitulo && !hasGenero) {
            result = repo.findByDirectorContainingIgnoreCase(director);
        } else {
            result = repo.findAll();
        }

        return result.stream().map(this::toResponse).toList();
    }


    private PeliculaResponse toResponse(Pelicula p) {
        return new PeliculaResponse(
                p.getId(),
                p.getTitulo(),
                p.getDirector(),
                p.getDuracion(),
                p.getValoracion(),
                p.getFechaEstreno(),
                p.getGenero()
        );
    }
}
