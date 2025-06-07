package com.alurachallenger.challenge_literalura.service;

import com.alurachallenger.challenge_literalura.model.Autor;
import com.alurachallenger.challenge_literalura.model.Libro;
import com.alurachallenger.challenge_literalura.repository.AutorRepository;
import com.alurachallenger.challenge_literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;

    public Libro guardarLibroDesdeDato(DatoLibro datoLibro) {
        // Paso 1: Obtener o crear autores asociados
        List<Autor> autoresAsociados = new ArrayList<>();

        for (DatoAutor datoAutor : datoLibro.autores()) { // asumiendo que datoLibro tiene esa info
            Optional<Autor> autorExistente = autorRepository.findByNombre(datoAutor.nombre());
            if (autorExistente.isPresent()) {
                autoresAsociados.add(autorExistente.get());
            } else {
                Autor nuevoAutor = new Autor(datoAutor);
                autorRepository.save(nuevoAutor);
                autoresAsociados.add(nuevoAutor);
            }
        }

        // Paso 2: Crear libro con esos autores
        Libro libro = new Libro(datoLibro, autoresAsociados);

        // Paso 3: Guardar libro
        return libroRepository.save(libro);
    }
    @Transactional
    public List<Libro> obtenerLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(libro -> libro.getIdiomas().size()); // inicialización forzada
        return libros;
    }
}
