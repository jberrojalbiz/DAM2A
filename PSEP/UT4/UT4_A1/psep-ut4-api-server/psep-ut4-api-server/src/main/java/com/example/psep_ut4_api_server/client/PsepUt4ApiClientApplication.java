package com.example.psep_ut4_api_server.client;

import com.example.psep_ut4_api_server.client.cli.MenuCli;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = "com.example.psep_ut4_api_server.client",
        exclude = {
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                SqlInitializationAutoConfiguration.class
        }
)
public class PsepUt4ApiClientApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PsepUt4ApiClientApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE); // cliente = consola
        app.run(args);
    }

    @Bean
    CommandLineRunner run(MenuCli menuCli) {
        return args -> menuCli.start();
    }
}
