package com.app.servicio_items;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

/**
 * Clase de configuración del rest template.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Crea y configura un bean de tipo {@link RestTemplate} para ser inyectado
     * en otros componentes de la aplicación.
     * 
     * @return Una nueva instancia de RestTemplate con configuración por defecto
     */
    @Bean("clienteRest")
    @LoadBalanced
    public RestTemplate registrarRestTemplate() {
        return new RestTemplate();
    }
}