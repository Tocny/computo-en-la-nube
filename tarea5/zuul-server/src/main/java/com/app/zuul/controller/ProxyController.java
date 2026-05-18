package com.app.zuul.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Controlador proxy que expone endpoints para demostrar las funcionalidades
 * de tolerancia a fallos y manejo de latencia con Hystrix, usando el
 * microservicio {@code servicio-autos}.
 * 
 * Endpoints
 *   {@code GET /api/proxy/autos/listar} – Lista los autos disponibles.</li>
 *   {@code GET /api/proxy/autos/lento}  – Simula una llamada con latencia
 *       excesiva para probar el timeout.
 * 
 */
@RestController
@RequestMapping("/api/proxy")
public class ProxyController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Obtiene la lista de autos desde el microservicio {@code servicio-autos}.
     * 
     * @return JSON con la lista de autos, o mensaje de error si falla
     */
    @GetMapping("/autos/listar")
    @HystrixCommand(fallbackMethod = "fallbackListarAutos",
                    commandProperties = {
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
                    })
    public String listarAutos() {
        return restTemplate.getForObject("http://servicio-autos/listar", String.class);
    }

    /**
     * Método de fallback para listrarAutos
     * Se ejecuta cuando el microservicio {@code servicio-autos} no responde
     * 
     * @return mensaje de error en formato JSON indicando la no disponibilidad.
     */
    public String fallbackListarAutos() {
        return "{\"error\":\"Servicio no disponible.\"}";
    }

        
    /**
     * Invoca un endpoint de latencia simulada en {@code servicio-autos}
     * que tarda 2 segundos en responder.
     * 
     * Como Hystrix está configurado para un timeout de 1sec,
     * esta llamada siempre ejecuta el fallback respectivo.
     * 
     * @return mensaje de timeout
     */
    @GetMapping("/autos/lento")
    @HystrixCommand(fallbackMethod = "fallbackLento",
                    commandProperties = {
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
                    })
    public String lento() {
        return restTemplate.getForObject("http://servicio-autos/lento", String.class);
    }

    /**
     * Método de fallback para el método de respuesta lenta.
     * Se ejecuta cuando la llamada al endpoint excede el timeout
     * 
     * @return Mensaje indicando que se superó timeout.
     */
    public String fallbackLento() {
        return "Timeout (>1s)";
    }
}