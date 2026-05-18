package com.app.servicio_autos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Clase principal de la aplicación Spring Boot para el servicio de autos.
 * Esta clase es responsable de iniciar y configurar la aplicación.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    /**
     * Método único, sirve como punto de entrada de la aplicación.
     * Inicia el contenedor de Spring y levanta la aplicación.
     *
     * @param args Argumentos de línea de comandos pasados a la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}