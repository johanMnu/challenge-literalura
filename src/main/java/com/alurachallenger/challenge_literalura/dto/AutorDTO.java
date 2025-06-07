package com.alurachallenger.challenge_literalura.dto;
import com.alurachallenger.challenge_literalura.model.Autor;
import java.util.List;
public record AutorDTO(
        String nombre,
        Integer fechaNacimiento,
        Integer fechaFallecimiento
){}