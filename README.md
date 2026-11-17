# LiterAlura — Catálogo de libros en Java

LiterAlura es una aplicación de **consola** que desarrollé durante el programa Oracle Next Education (ONE) / Alura. Permite buscar libros usando [Gutendex](https://gutendex.com/) y guardar la información en una base de datos PostgreSQL para consultarla después.

Fue uno de esos proyectos que me obligaron a investigar bastante: algunas consultas no salían como esperaba y tuve que entender mejor cómo relacionar autores y libros, transformar las respuestas JSON y trabajar con repositorios de Spring Data JPA.

## ¿Qué se puede hacer?

El programa muestra un menú con siete opciones:

1. Buscar un libro por título en Gutendex y registrarlo.
2. Listar los libros guardados.
3. Listar los autores registrados.
4. Buscar autores vivos en un año indicado.
5. Filtrar libros registrados por idioma.
6. Mostrar los diez libros registrados con más descargas.
7. Buscar autores por su nombre (o parte del nombre).

La opción `0` cierra el programa.

> Las opciones de listado y estadísticas trabajan sobre los libros **guardados localmente**, no sobre todos los libros de Gutendex.

## Tecnologías

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA / Hibernate
- PostgreSQL
- Jackson para leer las respuestas JSON
- Cliente HTTP nativo de Java para consultar Gutendex
- Maven

No es una API REST ni necesita un frontend: la interacción es mediante la terminal.

## ¿Cómo funciona?

Cuando buscás un título, la aplicación consulta `https://gutendex.com/books/?search=...`, convierte el JSON recibido en objetos Java y toma el primer resultado de la búsqueda.

Desde `LibroService` busca si los autores ya existen y los reutiliza o registra nuevos antes de guardar el libro. Las entidades `Libro` y `Autor` tienen una relación muchos a muchos.

La base de datos permite conservar la información entre ejecuciones y consultar autores, idiomas y descargas utilizando repositorios JPA.

## Cómo ejecutarlo

### Requisitos

- JDK 17 o superior
- PostgreSQL
- Git
- Maven, o Maven Wrapper si está disponible en tu entorno

1. Clonar el proyecto:

```bash
git clone https://github.com/johanMnu/challenge-literalura.git
cd challenge-literalura
```

2. Crear una base PostgreSQL (el nombre predeterminado es `literalura_DB`):

```sql
CREATE DATABASE "literalura_DB";
```

3. Configurar la contraseña del usuario local de PostgreSQL mediante la variable de entorno `DB_PASSWORD`. El proyecto también admite `DB_URL` y `DB_USERNAME` si tu instalación usa otros valores.

**PowerShell (Windows):**

```powershell
$env:DB_PASSWORD = "tu_contrasena_local"
./mvnw.cmd spring-boot:run
```

**Bash (Linux/macOS):**

```bash
export DB_PASSWORD="tu_contrasena_local"
./mvnw spring-boot:run
```

4. Cuando aparezca el menú, elegí una opción numérica y seguí las indicaciones de la consola. Para probar sin consultar la API externa, podés elegir primero las opciones de listado y luego salir con `0`.

**Importante:** no se debe guardar ni subir una contraseña real a GitHub. La configuración utiliza variables de entorno para que cada persona pueda ejecutar el programa con su propia base de datos.

## Estructura principal

| Carpeta | Qué contiene |
| --- | --- |
| `Principal/` | Menú e interacción con el usuario |
| `service/` | Consumo HTTP, lectura JSON y guardado de libros |
| `model/` | Entidades `Libro` y `Autor` |
| `repository/` | Consultas con Spring Data JPA |
| `dto/` | Objetos para representar datos |

## Qué aprendí y qué mejoraría

Practiqué el consumo de APIs, el uso de records para representar datos, las relaciones JPA y las consultas derivadas de Spring Data.

También descubrí que no alcanza con que la aplicación funcione una vez: es importante manejar búsquedas sin resultados, entradas inválidas y posibles fallos externos. Entre las mejoras que me gustaría incorporar están ampliar las pruebas automáticas, evitar guardar dos veces el mismo libro y manejar más casos de error en el menú.

Este proyecto refleja parte de mi aprendizaje con Java y las bases de datos; no lo presento como un producto terminado.
