package com.app.zuul.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración de un {@link RestTemplate} balanceado para microservicios registrados en Eureka.
 * 
 * {@link LoadBalanced} permite usar el nombre lógico del servicio
 * en lugar de una URL física, delegando el balanceo de carga a Ribbon.
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}