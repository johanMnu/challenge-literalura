package com.alurachallenger.challenge_literalura.service;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatoAutor(
                        Integer id,
                        @JsonAlias("name")
                        String nombre,
                        @JsonAlias("birth_year")
                        Integer fechaNacimiento,
                        @JsonAlias("death_year")
                        Integer fechaFallecimiento) {
}
