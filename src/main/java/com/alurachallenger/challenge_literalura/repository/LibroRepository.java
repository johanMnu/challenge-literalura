package com.alurachallenger.challenge_literalura.repository;
import com.alurachallenger.challenge_literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository  extends JpaRepository<Libro,Long>{

        Optional<Libro> findByTituloIgnoreCase(String titulo);
        List<Libro> findByIdiomasContainingIgnoreCase(String idioma);
        Optional<Libro> findByTitulo(String titulo);
        List<Libro> findTop10ByOrderByDescargasDesc();
}
