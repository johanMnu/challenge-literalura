package com.alurachallenger.challenge_literalura.model;

import com.alurachallenger.challenge_literalura.dto.AutorDTO;
import com.alurachallenger.challenge_literalura.service.DatoAutor;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;
    @ManyToMany(mappedBy = "autores" , fetch = FetchType.EAGER)
    private Set<Libro> libros = new HashSet<>();
    public Autor() {}
    public Autor(DatoAutor datoAutor) {
        this.nombre = datoAutor.nombre();
        this.fechaNacimiento = datoAutor.fechaNacimiento();
        this.fechaFallecimiento = datoAutor.fechaFallecimiento();
    }
    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaFallecimiento=" + fechaFallecimiento +
                '}';
    }
}
