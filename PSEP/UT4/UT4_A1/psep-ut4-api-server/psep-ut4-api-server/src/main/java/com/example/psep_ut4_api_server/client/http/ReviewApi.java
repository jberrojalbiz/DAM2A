package com.example.psep_ut4_api_server.client.http;

import com.example.psep_ut4_api_server.client.dto.ReviewDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;

import java.util.List;

@HttpExchange("/api/peliculas/{peliculaId}/reviews")
public interface ReviewApi {

    @GetExchange
    List<ReviewDto> getByPelicula(@PathVariable Long peliculaId);

    @PostExchange
    ReviewDto create(@PathVariable Long peliculaId, @RequestBody ReviewDto dto);
}
