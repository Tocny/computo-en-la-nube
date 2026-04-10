package com.app.servicio_items;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

/**
 * Cliente Feign para consumir el microservicio de autos.
 * Con Eureka, se elimina la URL fija y se usa solo el nombre del servicio.
 */
@FeignClient(name = "servicio-autos")
public interface AutoClienteRest {

    /**
     * Obtiene la lista de autos desde el microservicio.
     * 
     * @return Lista de objetos Auto obtenidos del servicio
     * @throws feign.FeignException si hay error en la comunicación.
     */
    @GetMapping("/listar")
    List<Auto> listar();

    /**
     * Obtiene los detalles de un auto específico por su ID.
     * 
     * @param id Identificador del auto a consultar
     * @return Objeto Auto con los detalles del vehículo.
     * @throws feign.FeignException si hay error en la comunicación.
     */
    @GetMapping("/ver/{id}")
    Auto detalle(@PathVariable Long id);

    /**
     * Elimina un auto específico por su ID.
     * 
     * @param id Identificador del auto a eliminar.
     * @return Mensaje de confirmación de la operación.
     * @throws feign.FeignException si hay error en la comunicación.
     */
    @DeleteMapping("/eliminar/{id}")
    String eliminar(@PathVariable Long id);
}