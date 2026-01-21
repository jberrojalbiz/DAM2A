package com.example.psep_ut4_api_server.client.cli;

import com.example.psep_ut4_api_server.client.dto.PeliculaDto;
import com.example.psep_ut4_api_server.client.dto.ReviewDto;
import com.example.psep_ut4_api_server.client.http.PeliculaApi;
import com.example.psep_ut4_api_server.client.http.ReviewApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Component
public class MenuCli {

    private final PeliculaApi api;
    private final ReviewApi reviewApi;

    public MenuCli(PeliculaApi api, ReviewApi reviewApi) {
        this.api = api;
        this.reviewApi = reviewApi;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        while (true) {
            System.out.println("""
                    
                    ==========================
                      CLIENTE - PELICULAS
                    ==========================
                    1) Listar películas
                    2) Buscar película por ID
                    3) Crear película
                    4) Actualizar película
                    5) Eliminar película
                    6) Cargar películas desde JSON (subir al servidor)
                    7) Guardar películas recibidas en JSON
                    8) Ver reseñas de una película
                    9) Añadir reseña a una película
                    10) Buscar películas (consulta personalizada)
                    0) Salir
                    """);

            System.out.print("Opción: ");
            String op = sc.nextLine().trim();

            try {
                switch (op) {
                    case "1" -> listarPeliculas();
                    case "2" -> buscarPelicula(sc);
                    case "3" -> crearPelicula(sc);
                    case "4" -> actualizarPelicula(sc);
                    case "5" -> eliminarPelicula(sc);
                    case "6" -> cargarJsonYSubir(sc, mapper);
                    case "7" -> guardarListadoJson(sc, mapper);
                    case "8" -> listarReviews(sc);
                    case "9" -> crearReview(sc);
                    case "10" -> buscarPersonalizada(sc);
                    case "0" -> {
                        System.out.println("👋 Saliendo...");
                        System.exit(0);
                    }
                    default -> System.out.println("Opción no válida.");
                }
            } catch (HttpClientErrorException.NotFound e) {
                System.out.println("❌ 404 - No encontrado.");
            } catch (HttpClientErrorException.BadRequest e) {
                System.out.println("❌ 400 - Datos inválidos.");
            } catch (HttpClientErrorException e) {
                System.out.println("❌ Error HTTP: " + e.getStatusCode());
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    // ------------------ PELICULAS ------------------

    private void listarPeliculas() {
        List<PeliculaDto> list = api.getAll();
        if (list.isEmpty()) {
            System.out.println("No hay películas.");
            return;
        }
        list.forEach(p -> System.out.println("• " + p));
    }

    private void buscarPelicula(Scanner sc) {
        Long id = pedirLong(sc, "ID: ");
        PeliculaDto peli = api.getById(id);
        System.out.println(peli);
    }

    private void crearPelicula(Scanner sc) {
        PeliculaDto dto = leerPelicula(sc, null);
        PeliculaDto created = api.create(dto);
        System.out.println("✅ Creada: " + created);
    }

    private void actualizarPelicula(Scanner sc) {
        Long id = pedirLong(sc, "ID a actualizar: ");
        PeliculaDto dto = leerPelicula(sc, id);
        PeliculaDto updated = api.update(id, dto);
        System.out.println("✅ Actualizada: " + updated);
    }

    private void eliminarPelicula(Scanner sc) {
        Long id = pedirLong(sc, "ID a eliminar: ");
        api.delete(id);
        System.out.println("✅ Eliminada (204).");
    }

    private void buscarPersonalizada(Scanner sc) {
        System.out.println("""
            BÚSQUEDA PERSONALIZADA
            - Deja vacío lo que no quieras filtrar.
            """);

        System.out.print("Título contiene: ");
        String titulo = sc.nextLine().trim();
        if (titulo.isBlank()) titulo = null;

        System.out.print("Género (exacto, ej: Sci-Fi): ");
        String genero = sc.nextLine().trim();
        if (genero.isBlank()) genero = null;

        System.out.print("Director contiene: ");
        String director = sc.nextLine().trim();
        if (director.isBlank()) director = null;

        List<PeliculaDto> results = api.search(titulo, genero, director);

        if (results.isEmpty()) {
            System.out.println("No hay resultados.");
            return;
        }

        System.out.println("Resultados:");
        results.forEach(p -> System.out.println("• " + p));
    }


    // ------------------ EXTRAS JSON ------------------

    private void cargarJsonYSubir(Scanner sc, ObjectMapper mapper) throws Exception {
        System.out.print("Ruta del JSON (ej: json-tests/peliculas_set3.json): ");
        String path = sc.nextLine().trim();

        List<PeliculaDto> lista = mapper.readValue(new File(path), new TypeReference<>() {});
        if (lista.isEmpty()) {
            System.out.println("El JSON está vacío. Nada que subir.");
            return;
        }

        for (PeliculaDto p : lista) {
            PeliculaDto created = api.create(new PeliculaDto(
                    null,
                    p.titulo(),
                    p.director(),
                    p.duracion(),
                    p.valoracion(),
                    p.fechaEstreno(),
                    p.genero()
            ));
            System.out.println("⬆️ Subida: " + created);
        }

        System.out.println("✅ Carga finalizada.");
    }

    private void guardarListadoJson(Scanner sc, ObjectMapper mapper) throws Exception {
        System.out.print("Ruta de salida (ej: out/peliculas_recibidas.json): ");
        String path = sc.nextLine().trim();

        File f = new File(path);
        if (f.getParentFile() != null) f.getParentFile().mkdirs();

        mapper.writerWithDefaultPrettyPrinter().writeValue(f, api.getAll());
        System.out.println("✅ Guardado en: " + path);
    }

    // ------------------ REVIEWS ------------------

    private void listarReviews(Scanner sc) {
        Long peliculaId = pedirLong(sc, "ID de la película: ");
        List<ReviewDto> reviews = reviewApi.getByPelicula(peliculaId);

        if (reviews.isEmpty()) {
            System.out.println("No hay reseñas para esta película.");
            return;
        }

        reviews.forEach(r -> System.out.println(
                "• [" + r.puntuacion() + "] " + r.autor() + " -> " + r.comentario() + " (" + r.fecha() + ")"
        ));
    }

    private void crearReview(Scanner sc) {
        Long peliculaId = pedirLong(sc, "ID de la película: ");

        System.out.print("Autor: ");
        String autor = sc.nextLine().trim();

        BigDecimal puntuacion = pedirBigDecimal(
                sc, "Puntuación (0-10, ej 8.5): ", BigDecimal.ZERO, new BigDecimal("10.0")
        );

        System.out.print("Comentario: ");
        String comentario = sc.nextLine().trim();

        ReviewDto dto = new ReviewDto(
                null,
                autor,
                puntuacion,
                comentario,
                LocalDateTime.now()
        );

        ReviewDto created = reviewApi.create(peliculaId, dto);
        System.out.println("✅ Reseña creada: " + created);
    }

    // ------------------ UTILIDADES ENTRADA ------------------

    private PeliculaDto leerPelicula(Scanner sc, Long id) {
        System.out.print("Titulo: ");
        String titulo = sc.nextLine().trim();

        System.out.print("Director: ");
        String director = sc.nextLine().trim();

        Integer duracion = pedirInt(sc, "Duracion (min): ", 1, 1000);

        BigDecimal valoracion = pedirBigDecimal(
                sc, "Valoracion (0-10, ej 8.5): ", BigDecimal.ZERO, new BigDecimal("10.0")
        );

        LocalDate fecha = pedirLocalDate(sc, "Fecha estreno (YYYY-MM-DD): ");

        System.out.print("Genero: ");
        String genero = sc.nextLine().trim();

        return new PeliculaDto(id, titulo, director, duracion, valoracion, fecha, genero);
    }

    private Long pedirLong(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                System.out.println("❌ Debes introducir un número entero válido.");
            }
        }
    }

    private Integer pedirInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(s);
                if (val < min || val > max) {
                    System.out.println("❌ Debe estar entre " + min + " y " + max + ".");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Debes introducir un número entero válido.");
            }
        }
    }

    private BigDecimal pedirBigDecimal(Scanner sc, String prompt, BigDecimal min, BigDecimal max) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().replace(",", ".");
            try {
                BigDecimal val = new BigDecimal(s);
                if (val.compareTo(min) < 0 || val.compareTo(max) > 0) {
                    System.out.println("❌ Debe estar entre " + min + " y " + max + ".");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Debes introducir un decimal válido (ej: 8.5).");
            }
        }
    }

    private LocalDate pedirLocalDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return LocalDate.parse(s);
            } catch (Exception e) {
                System.out.println("❌ Formato incorrecto. Usa YYYY-MM-DD (ej: 2025-01-20).");
            }
        }
    }
}
