

```markdown
# Literalura Project

Este proyecto es una aplicación web desarrollada con Spring Boot para gestionar autores y libros. La aplicación permite realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) en los recursos `Autor` y `Libro`.

## Requisitos

- Java 11 o superior
- Maven 3.6.3 o superior
- MySQL 5.7 o superior
- * API de [Gutendex](https://gutendex.com/?ref=public_apis)

## Instalación

### Clonar el repositorio

```bash
git clone https://github.com/tu_usuario/literalura.git
cd literalura
```

### Configuración de la base de datos

Crea una base de datos en MySQL o postgresql llamada `literalura`:

Actualiza el archivo `src/main/resources/application.properties` con tus credenciales de la base de datos :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Construir y ejecutar la aplicación

La aplicación estará disponible en `http://localhost:8080`.

## Endpoints de la API

### Autores

- `GET /autores`: Obtiene todos los autores.
- `POST /autores`: Crea un nuevo autor.
- `GET /autores/{id}`: Obtiene un autor por su ID.
- `PUT /autores/{id}`: Actualiza un autor por su ID.
- `DELETE /autores/{id}`: Elimina un autor por su ID.

### Libros

- `GET /libros`: Obtiene todos los libros.
- `POST /libros`: Crea un nuevo libro.
- `GET /libros/{id}`: Obtiene un libro por su ID.
- `PUT /libros/{id}`: Actualiza un libro por su ID.
- `DELETE /libros/{id}`: Elimina un libro por su ID.

## Documentación de la API

La documentación de la API está disponible en `http://localhost:8080/swagger-ui.html`.

## Estructura del proyecto

```bash
literalura
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── aluracursos
│   │   │           └── literalura
│   │   │               ├── config
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   └── SwaggerConfig.java
│   │   │               ├── controller
│   │   │               │   ├── AutorController.java
│   │   │               │   └── LibroController.java
│   │   │               ├── exception
│   │   │               │   └── ResourceNotFoundException.java
│   │   │               ├── model
│   │   │               │   ├── Autor.java
│   │   │               │   └── Libro.java
│   │   │               ├── repository
│   │   │               │   ├── AutorRepository.java
│   │   │               │   └── LibroRepository.java
│   │   │               └── service
│   │   │                   ├── AutorService.java
│   │   │                   └── LibroService.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       ├── java
│       │   └── com
│       │       └── aluracursos
│       │           └── literalura
│       │               ├── controller
│       │               │   ├── AutorControllerTest.java
│       │               │   └── LibroControllerTest.java
│       │               └── service
│       │                   ├── AutorServiceTest.java
│       │                   └── LibroServiceTest.java
└── pom.xml
```

## Pruebas

### Pruebas Unitarias

Para ejecutar las pruebas unitarias, usa el siguiente comando:

```bash
mvn test
```

### Pruebas de Integración

Las pruebas de integración se encuentran en el mismo directorio que las pruebas unitarias y se ejecutan con el mismo comando.

## Contribución

Las contribuciones son bienvenidas. Por favor, sigue los pasos a continuación para contribuir:

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza los cambios necesarios y confirma los cambios (`git commit -m 'Agregar nueva funcionalidad'`).
4. Empuja los cambios a tu repositorio fork (`git push origin feature/nueva-funcionalidad`).
5. Abre un pull request en GitHub.

## Licencia

Solo con la de James Bond "Licencia para editar" 😎
