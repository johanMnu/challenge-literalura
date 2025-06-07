package com.alurachallenger.challenge_literalura.Principal;

import com.alurachallenger.challenge_literalura.model.Autor;
import com.alurachallenger.challenge_literalura.model.Libro;
import com.alurachallenger.challenge_literalura.repository.AutorRepository;
import com.alurachallenger.challenge_literalura.repository.LibroRepository;
import com.alurachallenger.challenge_literalura.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
@Component
public class Principal implements CommandLineRunner {

    private final Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LibroService libroService;
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();


    @Override
    public void run(String... args) {
        mostrarMenu();
    }

    public void mostrarMenu() {
        var opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    Elija la opción a través de su número:
                    1 - Buscar libro por el título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Mostrar top 10 libros mas descargados
                    7 - Buscar autor por nombre
                    0 - Salir
                    """);

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 :
                    buscarLibroPorTitulo();
                    break;
                case 2 :
                    listarLibrosRegistrados();
                    break;
                case 3 :
                    listarAutoresRegistrados();
                    break;
                case 4 :
                    listarAutoresVivosPorAnio();
                    break;
                case 5 :
                    listarLibrosPorIdioma();
                    break;
                case 6 :
                    mostrarTop10LibrosMasDescargados();
                    break;
                case 7 : buscarAutorPorNombre();
                    break;
                case 0 :
                    System.out.println("Saliendo...");
                    break;
                default : System.out.println("Opción no válida.");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Ingrese el título del libro: ");
        var titulo = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + titulo.replace(" ", "+"));
        var respuesta = conversor.obtenerDatos(json, APIresponse.class);

        if (respuesta.getResults().isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
            return;
        }

        DatoLibro datoLibro = respuesta.getResults().get(0);
        libroService.guardarLibroDesdeDato(datoLibro);  // << usa el servicio
        mostrarInformacionLibro(datoLibro);
        System.out.println("Libro guardado exitosamente.");

        // Aquí deberías guardarlo si usás repositorio (LibroRepository.save(libro));
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        libros.forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());

            // Autores
            var autores = libro.getAutores();
            if (autores == null || autores.isEmpty()) {
                System.out.println("Autor(es): No disponible");
            } else {
                String nombresAutores = autores.stream()
                        .map(a -> a.getNombre())
                        .reduce((a1, a2) -> a1 + ", " + a2)
                        .orElse("");
                System.out.println("Autor(es): " + nombresAutores);
            }

            // Idiomas (asumo que tenés algo como List<String> idiomas o Set<String> idiomas)
            var idiomas = libro.getIdiomas();
            if (idiomas == null || idiomas.isEmpty()) {
                System.out.println("Idiomas: No disponible");
            } else {
                System.out.println("Idiomas: " + String.join(", ", idiomas));
            }

            // Descargas
            System.out.println("Número de descargas: " + libro.getDescargas());

            System.out.println("------------------------------------------------");
        });
    }

    private void listarAutoresRegistrados() {
        System.out.println(">> Autores registrados:");
        var autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        autores.forEach(autor -> {
            System.out.println("\nNombre: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
            System.out.println("Fecha de fallecimiento: " +
                    (autor.getFechaFallecimiento() != null ? autor.getFechaFallecimiento() : "N/A"));

            var libros = autor.getLibros();
            if (libros.isEmpty()) {
                System.out.println("Libros registrados: Ninguno");
            } else {
                System.out.println("Libros registrados:");
                libros.forEach(libro -> System.out.println(" - " + libro.getTitulo()));
            }
        });
    }

    private void listarAutoresVivosPorAnio() {
        System.out.print("Ingrese el año a consultar: ");
        Integer anio = Integer.parseInt(teclado.nextLine());
        var autores = autorRepository.findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(anio, anio);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            autores.forEach(a -> System.out.println("- " + a.getNombre()));
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Ingrese el código del idioma:" +
                "es- español " +
                "en- ingles " +
                "fr- frances ");
        String idioma = teclado.nextLine();
        var libros = libroRepository.findByIdiomasContainingIgnoreCase(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            libros.forEach(libro -> System.out.println("- " + libro.getTitulo()));
        }
    }
    private void mostrarInformacionLibro(DatoLibro datoLibro) {
        System.out.println("Título: " + datoLibro.titulo());

        var autores = datoLibro.autores();
        if (autores != null && !autores.isEmpty()) {
            System.out.print("Autor(es): ");
            String nombres = autores.stream()
                    .map(a -> a.nombre())
                    .reduce((a1, a2) -> a1 + ", " + a2)
                    .orElse("");
            System.out.println(nombres);
        } else {
            System.out.println("Autor(es): No disponible");
        }

        var idiomas = datoLibro.idiomas();
        if (idiomas != null && !idiomas.isEmpty()) {
            System.out.print("Idiomas: ");
            System.out.println(String.join(", ", idiomas));
        } else {
            System.out.println("Idiomas: No disponible");
        }

        System.out.println("Número de descargas: " + datoLibro.descargas());
    }
    private void mostrarTop10LibrosMasDescargados() {
        List<Libro> top10Libros = libroRepository.findTop10ByOrderByDescargasDesc();

        if (top10Libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("=== Top 10 libros más descargados ===");
        for (int i = 0; i < top10Libros.size(); i++) {
            Libro libro = top10Libros.get(i);
            String autores = libro.getAutores().stream()
                    .map(Autor::getNombre)
                    .reduce((a1, a2) -> a1 + ", " + a2)
                    .orElse("Sin autor");

            String idiomas = libro.getIdiomas().isEmpty() ? "Sin idiomas" : String.join(", ", libro.getIdiomas());

            System.out.printf("%d. %s | Autor(es): %s | Idioma(s): %s | Descargas: %d%n",
                    i + 1, libro.getTitulo(), autores, idiomas, libro.getDescargas());
        }
    }
    private void buscarAutorPorNombre() {
        System.out.print("Ingrese el nombre o parte del nombre del autor: ");
        String nombreBusqueda = teclado.nextLine();

        List<Autor> autoresEncontrados = autorRepository.findByNombreContainingIgnoreCase(nombreBusqueda);

        if (autoresEncontrados.isEmpty()) {
            System.out.println("No se encontraron autores con ese nombre.");
        } else {
            System.out.println("Autores encontrados:");
            for (Autor autor : autoresEncontrados) {
                System.out.printf("ID: %d | Nombre: %s | Fecha Nacimiento: %d | Fecha Fallecimiento: %s%n",
                        autor.getId(),
                        autor.getNombre(),
                        autor.getFechaNacimiento(),
                        autor.getFechaFallecimiento() != null ? autor.getFechaFallecimiento() : "N/A");
            }
        }
    }

}
