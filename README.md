
# Test Devsu - Microservicios


"Clone el repositorio y ejecute el proyecto en su IDE de preferencia.
 La base de datos MYSQL se creará automáticamente al ejecutar el proyecto para ambos microservicios.
 No olvide configurar las credenciales de la base de datos en el archivo para ambos microservicios `application.properties` ubicado en `src/main/resources/application.properties`."

---

## Configuración de la base de datos

Por favor, asegúrese de configurar las credenciales de la base de datos en el archivo `application.properties` ubicado en `src/main/resources/application.properties`.

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

---
- Microservicio1 = Clientes y Personas(PORT = 8080).
- **Swagger: http://localhost:8080/swagger-ui/index.html**
---
- Microservicio2 = Cuentas y Movimientos(PORT = 8081).
- **Swagger: http://localhost:8081/swagger-ui/index.html**
 ---

## Tecnologias

- **Java Development Kit (JDK) 17:** Asegúrese de tener instalado JDK 17 en su sistema. Puede descargarlo desde [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) o [OpenJDK](https://adoptopenjdk.net/).

- **Spring Boot: 2.7.15**.

- **Base de datos MYSQL**

- **Swagger:** Documentación de la API.








