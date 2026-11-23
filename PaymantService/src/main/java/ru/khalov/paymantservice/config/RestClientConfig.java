package ru.khalov.paymantservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient restClient(@Value("${card.base-url}") String baseUrl){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(20000);
        factory.setConnectTimeout(2000);

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }
}
