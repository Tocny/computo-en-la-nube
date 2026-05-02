package com.app.servicio_autos;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar operaciones relacionadas con autos.
 * Proporciona endpoints para listar, ver y eliminar autos del catálogo.
 */
@RestController
public class AutoController {

    private final List<Auto> autos = new ArrayList<>(); 

    /**
     * Constructor que inicializa el catálogo con algunos autos de ejemplo.
     */
    public AutoController() {
        inicializarCatalogo();
    }
    
    /**
     * Inicializa el catálogo de vehículos.
     */
    private void inicializarCatalogo() {
        autos.add(new Auto(1L, "GMC", "Acadia", "2026", 1352900.00)); 
        autos.add(new Auto(2L, "GMC", "Terrain", "2026", 802900.00)); 
        autos.add(new Auto(3L, "Ford", "Escape Híbrida", "2025", 801100.00)); 
        autos.add(new Auto(4L, "Ford", "Territory Híbrida", "2025", 719900.00)); 
        autos.add(new Auto(5L, "Toyota", "Corolla Cross HEV", "2024", 625900.00)); 
        autos.add(new Auto(6L, "Toyota", "Highlander HEV", "2024", 950900.00)); 
    }

    /**
     * Obtiene la lista completa de autos disponibles.
     * @return Lista de todos los autos en el catálogo.
     */
    @GetMapping("/listar")
    public List<Auto> listar() {
        //enseña el puerto.
        //autos.forEach(a -> a.setMarca(a.getMarca() + " (puerto " + System.getProperty("server.port", "8001") + ")"));
        //autos.forEach(a -> a.setMarca(a.getMarca() + " (puerto " + System.getProperty("server.port", "8003") + ")"));
        return autos;
    }

    /**
     * Obtiene un auto específico por su ID.
     * @param id Identificador único del auto
     * @return El auto encontrado o null si no existe
     */
    @GetMapping("/ver/{id}")
    public ResponseEntity<Auto> ver(@PathVariable Long id) {
        Optional<Auto> autoOptional = autos.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
        
        return autoOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un auto del catálogo por su ID.
     * @param id Identificador único del auto a eliminar
     * @return Mensaje de confirmación de la eliminación
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        boolean eliminado = autos.removeIf(a -> a.getId().equals(id));
        
        if (eliminado) {
            return ResponseEntity.ok("Auto con ID " + id + " eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un auto con ID: " + id);
        }
    }

    /**
     * Simplemente es un método que se tarda dos segundos en ejecutarse.
     * Es para probar responsividad a errores de latencia.
     * @return mensaje de respuesta lenta.
     */
    @GetMapping("/lento")
    public ResponseEntity<String> lento() throws InterruptedException {
        Thread.sleep(2000); // 2 segundos
        return ResponseEntity.ok("Respuesta lenta después de 2 segundos");
    }
}