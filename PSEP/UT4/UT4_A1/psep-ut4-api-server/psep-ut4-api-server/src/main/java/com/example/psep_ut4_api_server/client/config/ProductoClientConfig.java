package com.example.psep_ut4_api_server.client.config;

import com.example.psep_ut4_api_server.client.http.ProductoApiClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Configuración del cliente HTTP (RestClient + @HttpExchange).
 * Se carga SOLO cuando app.mode=client.
 */
@Configuration
@ConditionalOnProperty(name = "app.mode", havingValue = "client")
public class ProductoClientConfig {

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(RestClient.Builder builder,
                                                           org.springframework.core.env.Environment env) {
        String baseUrl = env.getProperty("client.base-url", "http://localhost:8080");

        RestClient restClient = builder.baseUrl(baseUrl).build();
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    }

    @Bean
    public ProductoApiClient productoApiClient(HttpServiceProxyFactory factory) {
        return factory.createClient(ProductoApiClient.class);
    }
}
