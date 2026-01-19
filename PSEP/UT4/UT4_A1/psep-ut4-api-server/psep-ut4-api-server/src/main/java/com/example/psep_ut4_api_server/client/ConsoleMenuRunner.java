package com.example.psep_ut4_api_server.client;

import com.example.psep_ut4_api_server.client.dto.ProductoDto;
import com.example.psep_ut4_api_server.client.service.ProductoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Menú de terminal del cliente.
 * <p>
 * Se carga SOLO si app.mode=client.
 * </p>
 */
@Configuration
@ConditionalOnProperty(name = "app.mode", havingValue = "client")
public class ConsoleMenuRunner {

    /**
     * Runner de consola: CRUD completo contra la API REST.
     */
    @Bean
    public CommandLineRunner run(ProductoService productoService) {
        return args -> {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("""
                        
                        ==========================
                        CLIENTE API (PSEP UT4)
                        ==========================
                        1) Listar productos
                        2) Buscar producto por ID
                        3) Crear producto
                        4) Actualizar producto
                        5) Eliminar producto
                        0) Salir
                        """);

                System.out.print("Opción: ");
                String option = sc.nextLine().trim();

                try {
                    switch (option) {
                        case "1" -> listar(productoService);
                        case "2" -> buscarPorId(productoService, sc);
                        case "3" -> crear(productoService, sc);
                        case "4" -> actualizar(productoService, sc);
                        case "5" -> eliminar(productoService, sc);
                        case "0" -> { System.out.println("Saliendo..."); return; }
                        default -> System.out.println("Opción no válida.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        };
    }

    private void listar(ProductoService service) {
        List<ProductoDto> productos = service.getAll();
        if (productos.isEmpty()) System.out.println("No hay productos.");
        else productos.forEach(p -> System.out.println(" - " + p));
    }

    private void buscarPorId(ProductoService service, Scanner sc) {
        System.out.print("ID: ");
        Long id = Long.parseLong(sc.nextLine());
        System.out.println(service.getById(id));
    }

    private void crear(ProductoService service, Scanner sc) {
        ProductoDto nuevo = leerProducto(sc, null);
        System.out.println("Creado: " + service.create(nuevo));
    }

    private void actualizar(ProductoService service, Scanner sc) {
        System.out.print("ID a actualizar: ");
        Long id = Long.parseLong(sc.nextLine());
        ProductoDto datos = leerProducto(sc, id);
        System.out.println("Actualizado: " + service.update(id, datos));
    }

    private void eliminar(ProductoService service, Scanner sc) {
        System.out.print("ID a eliminar: ");
        Long id = Long.parseLong(sc.nextLine());
        service.delete(id);
        System.out.println("Eliminado (204).");
    }

    private ProductoDto leerProducto(Scanner sc, Long id) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Stock: ");
        Integer stock = Integer.parseInt(sc.nextLine());

        System.out.print("Precio: ");
        Double precio = Double.parseDouble(sc.nextLine());

        System.out.print("Fecha alta (YYYY-MM-DD): ");
        LocalDate fechaAlta = LocalDate.parse(sc.nextLine());

        return new ProductoDto(id, nombre, stock, precio, fechaAlta);
    }
}
