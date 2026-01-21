package com.example.psep_ut4_api_server.server.review;

import com.example.psep_ut4_api_server.server.common.ResourceNotFoundException;
import com.example.psep_ut4_api_server.server.pelicula.Pelicula;
import com.example.psep_ut4_api_server.server.pelicula.PeliculaRepository;
import com.example.psep_ut4_api_server.server.review.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepo;
    private final PeliculaRepository peliculaRepo;

    public ReviewService(ReviewRepository reviewRepo, PeliculaRepository peliculaRepo) {
        this.reviewRepo = reviewRepo;
        this.peliculaRepo = peliculaRepo;
    }

    public List<ReviewResponse> getByPelicula(Long peliculaId) {
        return reviewRepo.findByPeliculaId(peliculaId).stream().map(this::toResponse).toList();
    }

    public ReviewResponse addToPelicula(Long peliculaId, ReviewRequest req) {
        Pelicula pelicula = peliculaRepo.findById(peliculaId)
                .orElseThrow(() -> new ResourceNotFoundException("Película con id " + peliculaId + " no existe"));

        Review review = new Review(null, req.autor(), req.puntuacion(), req.comentario(), req.fecha(), pelicula);
        return toResponse(reviewRepo.save(review));
    }

    private ReviewResponse toResponse(Review r) {
        return new ReviewResponse(r.getId(), r.getAutor(), r.getPuntuacion(), r.getComentario(), r.getFecha());
    }
}
