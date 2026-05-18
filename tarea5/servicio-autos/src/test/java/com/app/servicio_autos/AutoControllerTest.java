package com.app.servicio_autos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Clase de pruebas unitarias para el controlador {@link AutoController}.
 * Verifica el correcto funcionamiento de las operaciones CRUD (listar, ver, crear,
 * actualizar, eliminar) así como los casos de error.
 * 
 */
@SpringBootTest
@AutoConfigureMockMvc
class AutoControllerTest {

    /**
     * Cliente MockMvc que permite ejecutar peticiones HTTP simuladas
     * y verificar respuestas, códigos de estado y contenido.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Instancia real del controlador, utilizada para resetear el catálogo
     * antes de cada prueba y garantizar un estado predecible.
     */
    @Autowired
    private AutoController autoController;

    /**
     * Método de configuración ejecutado antes de cada prueba.
     * Reinicia el catálogo de autos a su estado inicial (6 autos con IDs 1-6)
     * y restablece el generador de IDs para que los nuevos autos comiencen desde el 7.
     */
    @BeforeEach
    void setUp() {
        // Vacía la lista actual y reinicia el contador de IDs
        autoController.autos.clear();
        autoController.idGenerator.set(6);
        // Vuelve a cargar los 6 autos de ejemplo
        autoController.inicializarCatalogo();
    }

    // test: listar() 

    /**
     * Prueba el endpoint GET /listar, debe devolver 6 autos iniciales.
     * Verifica código 200 y que la cantidad de elementos sea 6.
     */
    @Test
    void testListar() throws Exception {
        mockMvc.perform(get("/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6));
    }

    // test: ver()

    /**
     * Prueba GET /ver/{id} con un ID existente (1), debe retornar el auto correcto.
     * Verifica código 200 y que la marca sea "GMC".
     */
    @Test
    void testVerExistente() throws Exception {
        mockMvc.perform(get("/ver/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("GMC"));
    }

    /**
     * Prueba GET /ver/{id} con un ID inexistente (99) debe retornar 404.
     */
    @Test
    void testVerNoExistente() throws Exception {
        mockMvc.perform(get("/ver/99"))
                .andExpect(status().isNotFound());
    }

    // test: crear()

    /**
     * Prueba la creación de un nuevo auto mediante POST /crear.
     * Se espera código 201 Created, que se genere el ID 7 (siguiente al inicial)
     * y que la marca se almacene correctamente.
     */
    @Test
    void testCrear() throws Exception {
        String nuevoAutoJson = "{\"marca\":\"Tesla\",\"modelo\":\"Model 3\",\"anio\":\"2025\",\"precio\":550000.0}";
        mockMvc.perform(post("/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(nuevoAutoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.marca").value("Tesla"));
    }

    // test: Actualizar()

    /**
     * Prueba la actualización de un auto existente (ID 1) mediante PUT /actualizar/1.
     * Verifica código 200 y que el modelo cambie a "test".
     */
    @Test
    void testActualizarExistente() throws Exception {
        String autoActualizado = "{\"marca\":\"GMC\",\"modelo\":\"test\",\"anio\":\"2026\",\"precio\":1500000.0}";
        mockMvc.perform(put("/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autoActualizado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("test"));
    }

    /**
     * Prueba intentar actualizar un auto con ID inexistente (99) debe retornar 404.
     */
    @Test
    void testActualizarNoExistente() throws Exception {
        String autoActualizado = "{\"marca\":\"Nissan\",\"modelo\":\"Versa\",\"anio\":\"2024\",\"precio\":300000.0}";
        mockMvc.perform(put("/actualizar/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(autoActualizado))
                .andExpect(status().isNotFound());
    }

    // test: eliminar() 

    /**
     * Prueba la eliminación de un auto existente (ID 1) mediante DELETE /eliminar/1.
     * Verifica código 200 y el mensaje de confirmación.
     * Además, verifica que después de eliminar, el auto ya no existe (GET /ver/1 retorna 404).
     */
    @Test
    void testEliminarExistente() throws Exception {
        mockMvc.perform(delete("/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Auto con ID 1 eliminado correctamente."));
        
        // Verificar que ya no existe
        mockMvc.perform(get("/ver/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba intentar eliminar un auto con ID inexistente (99), debe retornar. 404.
     */
    @Test
    void testEliminarNoExistente() throws Exception {
        mockMvc.perform(delete("/eliminar/99"))
                .andExpect(status().isNotFound());
    }
}