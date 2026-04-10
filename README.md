# computo-en-la-nube
Repositorio para la materia computo en la nube.

## Tecnologías ocupadas.
- Java 21
- Maven 3.8+

# Tarea 2: Microservicios de Autos e Items.

## Para levantar los servicios:
### Para el servicio de autos:
```bash
cd servicio-autos
mvn spring-boot:run
```
Después pueden consultarse las urls:
- http://localhost:8001/listar
- http://localhost:8001/ver/2
### Para el servicio de items:
Ojo: Servicio-autos debe de estar levantado para que este servicio se comunique con él.
```bash
cd servicio-items
mvn spring-boot:run
```
Después pueden consultarse urls como:
- http://localhost:8002/feign/ver/2/cantidad/5

Además, también se pueden hacer peticiones del tipo:
```bash
curl -X DELETE https://localhost:8002/rest/eliminar/1
```

# Tarea 3: Implementación de la tarea 2 con Hystrix y Eureka Server.

## Para levantar los servicios:

### Levantar el Eureka Server:
```bash
cd eureka-server
mvn spring-boot:run
```
Se pueden consultar los microservicios que usan el manager en: http://localhost:8761/

### Levantar el Servicio de autos:
```bash
cd servicio-autos
mvn spring-boot:run
```
Después pueden consultarse las urls:
- http://localhost:8001/listar
- http://localhost:8001/ver/2
### Levantar el servicio de items:
Ojo: Servicio-autos debe de estar levantado para que este servicio se comunique con él.
```bash
cd servicio-items
mvn spring-boot:run
```
Después pueden consultarse urls como:
- http://localhost:8002/feign/ver/2/cantidad/5







