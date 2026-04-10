package com.app.servicio_items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Clase principal de la aplicación Spring Boot para el servicio de items.
 * utilizando Feign Client para la comunicación.
 */
@EnableFeignClients
@SpringBootApplication
public class Application {

    /**
     * Método que inicia la aplicación de items.
     * Este método arranca el contenedor de Spring Boot.
     *
     * @param args Argumentos.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}