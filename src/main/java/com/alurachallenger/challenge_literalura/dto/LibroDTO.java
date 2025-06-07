package com.alurachallenger.challenge_literalura.dto;
import com.alurachallenger.challenge_literalura.model.Autor;
import java.util.List;

public record LibroDTO(
        Long id,
        String titulo,
        List<Autor> autores,
        List<String> idiomas,
        Integer descargas
) {
}
