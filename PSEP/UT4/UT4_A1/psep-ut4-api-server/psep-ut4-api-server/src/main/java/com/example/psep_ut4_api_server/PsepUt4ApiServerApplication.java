package com.example.psep_ut4_api_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal del servidor.
 * <p>
 * Inicia la API RESTful que expone un CRUD completo sobre la entidad Producto.
 * </p>
 */

@SpringBootApplication
public class PsepUt4ApiServerApplication {

    /**
     * Punto de entrada de la aplicación Spring Boot.
     *
     * @param args argumentos de consola.
     */

	public static void main(String[] args) {
		SpringApplication.run(PsepUt4ApiServerApplication.class, args);
	}

}
