package com.example.psep_ut4_api_server.client;

import com.example.psep_ut4_api_server.client.cli.MenuCli;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.WebApplicationType;

@SpringBootApplication(scanBasePackages = "com.example.psep_ut4_api_server.client")
public class PsepUt4ApiClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(PsepUt4ApiClientApplication.class)
                .web(WebApplicationType.NONE) // ⬅️ CLAVE
                .run(args);
    }

    @Bean
    CommandLineRunner run(MenuCli menuCli) {
        return args -> menuCli.start();
    }
}
