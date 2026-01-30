package com.example.ejercicioBiblioteca.service;

import com.example.ejercicioBiblioteca.dto.AutorTopDTO;
import com.example.ejercicioBiblioteca.dto.BibliotecaConteoDTO;
import com.example.ejercicioBiblioteca.model.Biblioteca;
import com.example.ejercicioBiblioteca.model.Libro;
import com.example.ejercicioBiblioteca.repository.BibliotecaRepository;
import org.bson.Document;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class BibliotecaAdvancedService {

    private final MongoTemplate mongoTemplate;
    private final BibliotecaRepository bibliotecaRepo;

    public BibliotecaAdvancedService(MongoTemplate mongoTemplate, BibliotecaRepository bibliotecaRepo) {
        this.mongoTemplate = mongoTemplate;
        this.bibliotecaRepo = bibliotecaRepo;
    }

    // -------------------------
    // PARTE 1: AGREGACIONES
    // -------------------------

    // 1) /api/bibliotecas/libros/conteo
    public List<BibliotecaConteoDTO> conteoLibrosPorBiblioteca() {
        // Calcula tamaño del array "libros" y ordena desc
        Aggregation agg = newAggregation(
                project("nombre", "ubicacion")
                        .andExpression("size(ifNull(libros, []))").as("totalLibros"),
                sort(Sort.Direction.DESC, "totalLibros")
        );

        AggregationResults<Document> results =
                mongoTemplate.aggregate(agg, "bibliotecas", Document.class);

        List<BibliotecaConteoDTO> out = new ArrayList<>();
        for (Document d : results.getMappedResults()) {
            out.add(new BibliotecaConteoDTO(
                    d.getObjectId("_id").toString(),
                    d.getString("nombre"),
                    d.getString("ubicacion"),
                    d.getInteger("totalLibros", 0)
            ));
        }
        return out;
    }

    // 2) /api/autores/top  -> top 5 autores con más libros en todas las bibliotecas
    public List<AutorTopDTO> top5Autores() {
        /*
          Como "libros" es @DBRef, en Mongo suele guardarse como:
          libros: [ { "$ref": "libros", "$id": ObjectId("...") }, ... ]
          Por eso hacemos:
          - unwind libros
          - lookup a colección libros usando libros.$id -> _id
          - unwind libroData
          - group por autor contando
          - sort desc
          - limit 5
        */
        Aggregation agg = newAggregation(
                unwind("libros", true),
                lookup("libros", "libros.$id", "_id", "libroData"),
                unwind("libroData", true),
                group("libroData.autor").count().as("totalLibros"),
                sort(Sort.Direction.DESC, "totalLibros"),
                limit(5)
        );

        AggregationResults<Document> results =
                mongoTemplate.aggregate(agg, "bibliotecas", Document.class);

        List<AutorTopDTO> out = new ArrayList<>();
        for (Document d : results.getMappedResults()) {
            out.add(new AutorTopDTO(
                    d.getString("_id"), // autor
                    d.getInteger("totalLibros", 0)
            ));
        }
        return out;
    }

    // 3) /api/bibliotecas/libros/mayor/{cantidad}
    public List<BibliotecaConteoDTO> bibliotecasConMasDeXLibros(int cantidad) {

        Aggregation agg = Aggregation.newAggregation(
                // 1) Calculamos totalLibros = size(ifNull(libros, []))
                Aggregation.project("nombre", "ubicacion")
                        .andExpression("size(ifNull(libros, []))").as("totalLibros"),

                // 2) Filtramos por totalLibros > cantidad
                Aggregation.match(org.springframework.data.mongodb.core.query.Criteria.where("totalLibros").gt(cantidad)),

                // 3) Ordenamos desc
                Aggregation.sort(org.springframework.data.domain.Sort.Direction.DESC, "totalLibros")
        );

        AggregationResults<org.bson.Document> results =
                mongoTemplate.aggregate(agg, "bibliotecas", org.bson.Document.class);

        List<BibliotecaConteoDTO> out = new java.util.ArrayList<>();
        for (org.bson.Document d : results.getMappedResults()) {
            out.add(new BibliotecaConteoDTO(
                    d.getObjectId("_id").toString(),
                    d.getString("nombre"),
                    d.getString("ubicacion"),
                    d.getInteger("totalLibros", 0)
            ));
        }
        return out;
    }


    // -------------------------
    // PARTE 2: PAGINACIÓN
    // -------------------------

    // /api/bibliotecas/{id}/libros?page={pagina}&size={tamano}
    public Page<Libro> librosPaginados(String bibliotecaId, int page, int size) {
        Biblioteca b = bibliotecaRepo.findById(bibliotecaId)
                .orElseThrow(() -> new NoSuchElementException("Biblioteca no encontrada: " + bibliotecaId));

        List<Libro> sorted = b.getLibros().stream()
                .sorted(Comparator.comparing(Libro::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        int start = Math.max(0, page * size);
        int end = Math.min(sorted.size(), start + size);

        List<Libro> content = (start >= sorted.size()) ? List.of() : sorted.subList(start, end);

        return new PageImpl<>(content, PageRequest.of(page, size), sorted.size());
    }

    // -------------------------
    // PARTE 3: TRANSACCIÓN
    // -------------------------

    // /api/bibliotecas/migrar-libros?origen=...&destino=...
    @Transactional
    public Biblioteca migrarLibros(String origenId, String destinoId) {
        if (origenId.equals(destinoId)) {
            throw new IllegalArgumentException("Origen y destino no pueden ser la misma biblioteca");
        }

        Biblioteca origen = bibliotecaRepo.findById(origenId)
                .orElseThrow(() -> new NoSuchElementException("Biblioteca origen no encontrada: " + origenId));

        Biblioteca destino = bibliotecaRepo.findById(destinoId)
                .orElseThrow(() -> new NoSuchElementException("Biblioteca destino no encontrada: " + destinoId));

        // Regla: si algún libro YA existe en destino, rollback
        Set<String> titulosDestino = destino.getLibros().stream()
                .map(l -> l.getTitulo() == null ? "" : l.getTitulo().toLowerCase())
                .collect(Collectors.toSet());

        for (Libro l : origen.getLibros()) {
            String t = (l.getTitulo() == null) ? "" : l.getTitulo().toLowerCase();
            if (titulosDestino.contains(t)) {
                throw new IllegalStateException("Transacción cancelada: el destino ya tiene el libro '" + l.getTitulo() + "'");
            }
        }

        // Mover referencias
        destino.getLibros().addAll(origen.getLibros());
        Biblioteca destinoActualizada = bibliotecaRepo.save(destino);

        // Eliminar biblioteca origen (como pide el enunciado)
        bibliotecaRepo.deleteById(origenId);

        return destinoActualizada;
    }
}
