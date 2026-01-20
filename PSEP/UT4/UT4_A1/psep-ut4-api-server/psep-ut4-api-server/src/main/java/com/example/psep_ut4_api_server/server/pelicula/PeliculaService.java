package com.example.psep_ut4_api_server.server.pelicula;

import com.example.psep_ut4_api_server.server.common.ResourceNotFoundException;
import com.example.psep_ut4_api_server.server.pelicula.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {

    private final PeliculaRepository repo;

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
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Película con id " + id + " no existe");
        }
        repo.deleteById(id);
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
