package com.app.servicio_autos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Clase de pruebas unitarias para verificar que el contexto de Spring
 * se carga correctamente en la aplicación.
 */
@SpringBootTest
class ApplicationTests {

    /**
     * Prueba básica que verifica que el contexto de Spring Boot
     * 
     * @throws Exception si ocurre algún error durante la carga del contexto
     */
    @Test
    void contextLoads() {	
		//no hay nada porque SpringBootTest ya intenta cargar todo.
    }

}