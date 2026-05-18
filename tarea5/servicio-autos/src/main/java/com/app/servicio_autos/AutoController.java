package com.app.servicio_autos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AutoController {

    /** Logger. */
    private static final Logger log = LoggerFactory.getLogger(AutoController.class);

    /** Lista en memoria que almacena los autos */
    public final List<Auto> autos = new ArrayList<>();

    /** Generador de IDs atómicos. */
    public final AtomicLong idGenerator = new AtomicLong(6);

    /**
     * Constructor del controlador. Inicializa el catálogo con datos de ejemplo.
     */
    public AutoController() {
        inicializarCatalogo();
    }

    /**
     * Llena la lista de autos con algunos vehículos de muestra.
     */
    public void inicializarCatalogo() {
        autos.add(new Auto(1L, "GMC", "Acadia", "2026", 1352900.00));
        autos.add(new Auto(2L, "GMC", "Terrain", "2026", 802900.00));
        autos.add(new Auto(3L, "Ford", "Escape Híbrida", "2025", 801100.00));
        autos.add(new Auto(4L, "Ford", "Territory Híbrida", "2025", 719900.00));
        autos.add(new Auto(5L, "Toyota", "Corolla Cross HEV", "2024", 625900.00));
        autos.add(new Auto(6L, "Toyota", "Highlander HEV", "2024", 950900.00));
    }

    /**
     * Obtiene la lista completa de autos.
     * 
     * @return Lista de todos los autos almacenados.
     */
    @GetMapping("/listar")
    public List<Auto> listar() {
        log.info("GET /listar - devolviendo {} autos", autos.size());
        return autos;
    }

    /**
     * Busca y retorna un auto por su ID.
     * 
     * @param id Identificador único del auto.
     * @return ResponseEntity con el auto encontrado o 404 si no existe.
     */
    @GetMapping("/ver/{id}")
    public ResponseEntity<Auto> ver(@PathVariable Long id) {
        log.info("GET /ver/{}", id);
        Optional<Auto> autoOptional = autos.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
        if (autoOptional.isPresent()) {
            return ResponseEntity.ok(autoOptional.get());
        } else {
            log.warn("GET /ver/{} - no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo auto.
     * 
     * @param auto Objeto Auto recibido en el cuerpo de la petición.
     * @return ResponseEntity con el auto creado y código de estado 201 Created.
     */
    @PostMapping("/crear")
    public ResponseEntity<Auto> crear(@RequestBody Auto auto) {
        Long nuevoId = idGenerator.incrementAndGet();
        auto.setId(nuevoId);
        autos.add(auto);
        log.info("POST /crear - nuevo ID {}", nuevoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(auto);
    }

    /**
     * Actualiza los datos de un auto existente.
     * 
     * @param id ID del auto a actualizar.
     * @param autoActualizado Objeto con los nuevos datos.
     * @return ResponseEntity con el auto actualizado o 404  si el ID no existe.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Auto> actualizar(@PathVariable Long id, @RequestBody Auto autoActualizado) {
        log.info("PUT /actualizar/{}", id);
        Optional<Auto> autoOptional = autos.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
        if (autoOptional.isPresent()) {
            Auto auto = autoOptional.get();
            auto.setMarca(autoActualizado.getMarca());
            auto.setModelo(autoActualizado.getModelo());
            auto.setAnio(autoActualizado.getAnio());
            auto.setPrecio(autoActualizado.getPrecio());
            log.info("PUT /actualizar/{} - éxito", id);
            return ResponseEntity.ok(auto);
        } else {
            log.warn("PUT /actualizar/{} - no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un auto del catálogo por su ID.
     * 
     * @param id ID del auto a eliminar.
     * @return ResponseEntity con mensaje de éxito o 404  si el ID no existe.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        log.info("DELETE /eliminar/{}", id);
        boolean eliminado = autos.removeIf(a -> a.getId().equals(id));
        if (eliminado) {
            log.info("DELETE /eliminar/{} - éxito", id);
            return ResponseEntity.ok("Auto con ID " + id + " eliminado correctamente.");
        } else {
            log.warn("DELETE /eliminar/{} - no encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un auto con ID: " + id);
        }
    }

    /**
     * Endpoint de prueba que simula una respuesta lenta (2 segundos de espera).
     * para probar timeouts y tolerancia a fallos.
     * 
     * @return Mensaje indicando que la respuesta fue retardada.
     * @throws InterruptedException Si el hilo es interrumpido mientras duerme.
     */
    @GetMapping("/lento")
    public ResponseEntity<String> lento() throws InterruptedException {
        log.info("GET /lento - iniciando");
        Thread.sleep(2000);
        log.info("GET /lento - finalizado");
        return ResponseEntity.ok("Respuesta lenta después de 2 segundos");
    }

    /**
     * Reinicia el catálogo a su estado inicial.
     * Limpia la lista y vuelve a cargar los datos de ejemplo.
     */
    public void resetCatalogo() {
        autos.clear();
        idGenerator.set(6);
        inicializarCatalogo();
    }
}