package com.app.servicio_autos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AutoViewController {

    /**
     * Muestra la página principal del catálogo de autos.
     * La vista 'autos.html' contiene la tabla HTML y los scripts necesarios
     * para listar, crear, editar y eliminar autos mediante peticiones.
     * 
     * @return Nombre de la plantilla Thymeleaf a renderizar (autos.html)
     */
    @GetMapping("/")
    public String listaAutos() {
        // Se hace con el fetch a listar, solo regresamos el template..
        return "autos";
    }
}