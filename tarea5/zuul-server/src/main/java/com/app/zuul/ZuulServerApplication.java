package com.app.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Entrada de Zuul como api gateway
 * 
 * Actua como punto de acceso a los microservicios registrados en Eureka.
 * 
 * 
 * {@link SpringBootApplication} – Configuración de Spring Boot.
 * {@link EnableZuulProxy}  – Habilita el API Gateway con Zuul (usa
 *       Ribbon para balanceo de carga).
 *  {@link EnableDiscoveryClient} – Para registrar el gateway en
 *       en Eureka y acceda a los servicios.
 *  {@link EnableHystrix} – Activa el soporte para métodos fallback con Hystrix.
 * 
 * @see com.app.zuul.controller.ProxyController
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableHystrix
public class ZuulServerApplication {

    /**
     * Arranca el servidor Zuul.
     * 
     * @param args argumentos
     */
    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }
}