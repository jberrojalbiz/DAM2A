package com.example.psep_ut4_api_server.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Aplicación Spring Boot del cliente (terminal).
 * <p>
 * Se ejecuta cuando app.mode=client y NO levanta servidor web.
 * </p>
 */
@SpringBootApplication(scanBasePackages = "com.example.psep_ut4_api_server.client")
public class ApiClientApplication {

    /**
     * Arranque del cliente en modo consola (sin servidor embebido).
     *
     * @param args args.
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
