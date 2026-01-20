package com.example.psep_ut4_api_server.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.psep_ut4_api_server.server")
public class PsepUt4ApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsepUt4ApiServerApplication.class, args);
    }
}
