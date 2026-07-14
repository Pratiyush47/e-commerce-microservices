package com.ecommerce.order.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final HttpServletRequest request;

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((req, body, execution) -> {

            String authHeader = request.getHeader("Authorization");

            if (authHeader != null) {
                req.getHeaders().set("Authorization", authHeader);
            }

            return execution.execute(req, body);
        });

        return restTemplate;
    }
}