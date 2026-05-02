package com.app.servicio_items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador REST que expone endpoints para gestionar items.
 * Este controlador demuestra dos formas de consumir el microservicio de autos:
 * - RestTemplate (con Eureka usando @LoadBalanced)
 * - Feign Client (con Eureka y Hystrix para fallback)
 */
@RestController
public class ItemController {

    /**
     * Cliente RestTemplate para consumo tradicional de APIs REST.
     * Debe tener @LoadBalanced para usar el nombre del servicio.
     */
    @Autowired
    private RestTemplate clienteRest;

    /**
     * Cliente Feign declarativo para consumo de APIs REST.
     */
    @Autowired
    private AutoClienteRest clienteFeign;

    // Rest Template (con Eureka - usa nombre del servicio, no localhost):

    /**
     * Lista los autos disponibles usando RestTemplate.
     * Convierte cada auto en un Item con cantidad 1.
     * 
     * @return Lista de items.
     */
    @GetMapping("/rest/listar")
    public List<Item> listarRest() {
        // AHORA usa el NOMBRE del servicio, no localhost:8001
        Auto[] autos = clienteRest.getForObject("http://servicio-autos/listar", Auto[].class);
        
        // Convierte cada auto en un Item con cantidad 1
        return Arrays.stream(autos)
                .map(auto -> new Item(auto, 1))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un auto específico con una cantidad personalizada usando RestTemplate.
     * 
     * @param id Identificador del auto
     * @param cantidad Cantidad deseada para el item
     * @return Item con el auto solicitado y la cantidad especificada
     */
    @GetMapping("/rest/ver/{id}/cantidad/{cantidad}")
    public Item detalleRest(@PathVariable Long id, @PathVariable Integer cantidad) {
        // AHORA usa el NOMBRE del servicio
        Auto auto = clienteRest.getForObject("http://servicio-autos/ver/{id}", Auto.class, Map.of("id", id));
        return new Item(auto, cantidad);
    }

    /**
     * Elimina un auto específico usando RestTemplate.
     * 
     * @param id Identificador del auto a eliminar
     * @return Mensaje de confirmación de la eliminación
     */
    @DeleteMapping("/rest/eliminar/{id}")
    public String eliminarRest(@PathVariable Long id) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        
        // AHORA usa el NOMBRE del servicio
        clienteRest.delete("http://servicio-autos/eliminar/{id}", pathVariables);
        
        return "Item (Auto) con ID " + id + " eliminado con exito usando RestTemplate.";
    }

    // Feign:

    /**
     * Lista los autos disponibles usando Feign Client con Hystrix.
     * Convierte cada auto en un Item con cantidad 1.
     * 
     * @return Lista de items (o fallback si el servicio falla)
     */
    @GetMapping("/feign/listar")
    @HystrixCommand(fallbackMethod = "fallbackListarFeign")
    public List<Item> listarFeign() {
        // Usa el cliente Feign que ahora obtiene la dirección desde Eureka
        return clienteFeign.listar().stream()
                .map(auto -> new Item(auto, 1))
                .collect(Collectors.toList());
    }

    /**
     * Método de fallback para listarFeign.
     * Se ejecuta cuando el microservicio servicio-autos no responde.
     * 
     * @return Lista de items con datos por defecto
     */
    public List<Item> fallbackListarFeign() {
        // Retorna un item por defecto indicando que el servicio no está disponible
        Auto autoFallback = new Auto();
        autoFallback.setId(0L);
        autoFallback.setMarca("SERVICIO NO DISPONIBLE");
        autoFallback.setModelo("Hystrix Fallback.");
        autoFallback.setAnio("0");
        autoFallback.setPrecio(0.0);
        
        return Arrays.asList(new Item(autoFallback, 0));
    }

    /**
     * Obtiene un auto específico con una cantidad personalizada usando Feign Client con Hystrix.
     * 
     * @param id Identificador del auto
     * @param cantidad Cantidad deseada para el item
     * @return Item con el auto solicitado y la cantidad especificada
     */
    @GetMapping("/feign/ver/{id}/cantidad/{cantidad}")
    @HystrixCommand(fallbackMethod = "fallbackDetalleFeign")
    public Item detalleFeign(@PathVariable Long id, @PathVariable Integer cantidad) {
        Auto auto = clienteFeign.detalle(id);
        return new Item(auto, cantidad);
    }

    /**
     * Método de fallback para detalleFeign.
     * Se ejecuta cuando el microservicio servicio-autos no responde.
     * 
     * @param id Identificador del auto (recibido del método original)
     * @param cantidad Cantidad solicitada (recibida del método original)
     * @return Item con datos por defecto
     */
    public Item fallbackDetalleFeign(Long id, Integer cantidad) {
        Auto autoFallback = new Auto();
        autoFallback.setId(id);
        autoFallback.setMarca("SERVICIO NO DISPONIBLE");
        autoFallback.setModelo("Hystrix Fallback: Auto no encontrado");
        autoFallback.setAnio("0");
        autoFallback.setPrecio(0.0);
        
        return new Item(autoFallback, 0);
    }

    /**
     * Elimina un auto específico usando Feign Client (sin Hystrix en DELETE generalmente).
     * 
     * @param id Identificador del auto a eliminar
     * @return Mensaje de confirmación de la eliminación
     */
    @DeleteMapping("/feign/eliminar/{id}")
    public String eliminarFeign(@PathVariable Long id) {
        clienteFeign.eliminar(id);
        return "Item (Auto) con ID " + id + " eliminado con exito usando Feign.";
    }
}