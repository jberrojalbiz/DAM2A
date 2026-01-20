package com.example.psep_ut4_api_server.client.cli;

import com.example.psep_ut4_api_server.client.dto.PeliculaDto;
import com.example.psep_ut4_api_server.client.http.PeliculaApi;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class MenuCli {

    private final PeliculaApi api;

    public MenuCli(PeliculaApi api) {
        this.api = api;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    
                    ==========================
                      CLIENTE - PELICULAS
                    ==========================
                    1) Listar
                    2) Buscar por ID
                    3) Crear
                    4) Actualizar
                    5) Eliminar
                    0) Salir
                    """);

            System.out.print("Opción: ");
            String op = sc.nextLine().trim();

            try {
                switch (op) {
                    case "1" -> listar();
                    case "2" -> buscar(sc);
                    case "3" -> crear(sc);
                    case "4" -> actualizar(sc);
                    case "5" -> eliminar(sc);
                    case "0" -> System.exit(0);
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

    private void listar() {
        List<PeliculaDto> list = api.getAll();
        if (list.isEmpty()) System.out.println("No hay películas.");
        else list.forEach(p -> System.out.println("• " + p));
    }

    private void buscar(Scanner sc) {
        System.out.print("ID: ");
        Long id = Long.parseLong(sc.nextLine());
        System.out.println(api.getById(id));
    }

    private void crear(Scanner sc) {
        PeliculaDto dto = leer(sc, null);
        PeliculaDto created = api.create(dto);
        System.out.println("✅ Creada: " + created);
    }

    private void actualizar(Scanner sc) {
        System.out.print("ID a actualizar: ");
        Long id = Long.parseLong(sc.nextLine());
        PeliculaDto dto = leer(sc, id);
        PeliculaDto updated = api.update(id, dto);
        System.out.println("✅ Actualizada: " + updated);
    }

    private void eliminar(Scanner sc) {
        System.out.print("ID a eliminar: ");
        Long id = Long.parseLong(sc.nextLine());
        api.delete(id);
        System.out.println("✅ Eliminada (204).");
    }

    private PeliculaDto leer(Scanner sc, Long id) {
        System.out.print("Titulo: ");
        String titulo = sc.nextLine();

        System.out.print("Director: ");
        String director = sc.nextLine();

        System.out.print("Duracion (min): ");
        Integer duracion = Integer.parseInt(sc.nextLine());

        System.out.print("Valoracion (0-10, ej 8.5): ");
        BigDecimal valoracion = new BigDecimal(sc.nextLine());

        System.out.print("Fecha estreno (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine());

        System.out.print("Genero: ");
        String genero = sc.nextLine();

        return new PeliculaDto(id, titulo, director, duracion, valoracion, fecha, genero);
    }
}
