package com.app.servicio_items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador REST que expone endpoints para gestionar items.
 * Este controlador demuestra dos formas de consumir el microservicio de autos:
 */
@RestController
public class ItemController {

    /**
     * Cliente RestTemplate para consumo tradicional de APIs REST.
     */
    @Autowired
    private RestTemplate clienteRest;

    /**
     * Cliente Feign declarativo para consumo de APIs REST.
     */
    @Autowired
    private AutoClienteRest clienteFeign;

    // Rest Template:

    /**
     * Lista los autos disponibles usando RestTemplate.
     * Convierte cada auto en un Item con cantidad 1.
     * 
     * @return Lista de items.
     */
    @GetMapping("/rest/listar")
    public List<Item> listarRest() {
        // Realiza petición GET al microservicio de autos
        Auto[] autos = clienteRest.getForObject("http://localhost:8001/listar", Auto[].class);
        
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
        // Prepara las variables de path para la URL
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        
        // Realiza petición GET con variables de path
        Auto auto = clienteRest.getForObject("http://localhost:8001/ver/{id}", Auto.class, pathVariables);
        
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
        
        // Realiza petición DELETE al microservicio
        clienteRest.delete("http://localhost:8001/eliminar/{id}", pathVariables);
        
        return "Item (Auto) con ID " + id + " eliminado con exito usando RestTemplate.";
    }

    // Feign:

    /**
     * Lista los autos disponibles usando Feign Client.
     * Convierte cada auto en un Item con cantidad 1.
     * 
     * @return Lista de items
     */
    @GetMapping("/feign/listar")
    public List<Item> listarFeign() {
        // Usa el cliente Feign que abstrae toda la comunicación HTTP
        return clienteFeign.listar().stream()
                .map(auto -> new Item(auto, 1))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un auto específico con una cantidad personalizada usando Feign Client.
     * 
     * @param id Identificador del auto
     * @param cantidad Cantidad deseada para el item
     * @return Item con el auto solicitado y la cantidad especificada
     */
    @GetMapping("/feign/ver/{id}/cantidad/{cantidad}")
    public Item detalleFeign(@PathVariable Long id, @PathVariable Integer cantidad) {
        Auto auto = clienteFeign.detalle(id);
        return new Item(auto, cantidad);
    }

    /**
     * Elimina un auto específico usando Feign Client.
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