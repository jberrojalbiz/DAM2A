package com.example.psep_ut4_api_server.server.review;

import com.example.psep_ut4_api_server.server.review.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas/{peliculaId}/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> list(@PathVariable Long peliculaId) {
        return ResponseEntity.ok(service.getByPelicula(peliculaId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> create(@PathVariable Long peliculaId, @Valid @RequestBody ReviewRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addToPelicula(peliculaId, req));
    }
}
