package com.example.psep_ut4_api_server.client.config;

import com.example.psep_ut4_api_server.client.http.PeliculaApi;
import com.example.psep_ut4_api_server.client.http.ReviewApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClienteConfig {

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(RestClient.Builder builder) {
        RestClient restClient = builder
                .baseUrl("http://localhost:8081")
                .build();

        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();
    }

    @Bean
    public PeliculaApi peliculaApi(HttpServiceProxyFactory factory) {
        return factory.createClient(PeliculaApi.class);
    }

    @Bean
    public ReviewApi reviewApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ReviewApi.class);
    }

}
