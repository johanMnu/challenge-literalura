package com.alurachallenger.challenge_literalura.repository;
import com.alurachallenger.challenge_literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(int nacimiento, int muerte);
    Optional<Autor> findByNombre(String nombre);
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
}
