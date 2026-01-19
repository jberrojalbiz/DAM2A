package com.example.psep_ut4_api_server;

import com.example.psep_ut4_api_server.client.ApiClientApplication;
import com.example.psep_ut4_api_server.server.ApiServerApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.AbstractEnvironment;

/**
 * Lanzador principal del proyecto.
 * <p>
 * En función de la propiedad <b>app.mode</b>, arranca:
 * <ul>
 *   <li><b>server</b>: API RESTful</li>
 *   <li><b>client</b>: menú de terminal que consume la API</li>
 * </ul>
 * </p>
 */
public class AppLauncher {

    /**
     * Punto de entrada. Lee la propiedad 'app.mode' y arranca el contexto adecuado.
     *
     * @param args argumentos de consola.
     */
    public static void main(String[] args) {
        // Permite pasar -Dapp.mode=client o -Dapp.mode=server
        String mode = System.getProperty("app.mode", "server");

        // También permitimos pasarlo como --app.mode=client en args (Spring lo entiende),
        // pero aquí aseguramos un valor por defecto.
        System.setProperty("app.mode", mode);

        if ("client".equalsIgnoreCase(mode)) {
            SpringApplication app = new SpringApplication(ApiClientApplication.class);
            app.setAdditionalProfiles("client");
            app.run(args);
        } else {
            SpringApplication app = new SpringApplication(ApiServerApplication.class);
            app.setAdditionalProfiles("server");
            app.run(args);
        }
    }
}
