package com.example.psep_ut4_api_server.client.http;

import com.example.psep_ut4_api_server.client.dto.PeliculaDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@HttpExchange("/api/peliculas")
public interface PeliculaApi {

    @GetExchange
    List<PeliculaDto> getAll();

    @GetExchange("/{id}")
    PeliculaDto getById(@PathVariable Long id);

    @PostExchange
    PeliculaDto create(@RequestBody PeliculaDto dto);

    @PutExchange("/{id}")
    PeliculaDto update(@PathVariable Long id, @RequestBody PeliculaDto dto);

    @DeleteExchange("/{id}")
    void delete(@PathVariable Long id);

    @GetExchange("/search")
    List<PeliculaDto> search(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String director
    );

}
