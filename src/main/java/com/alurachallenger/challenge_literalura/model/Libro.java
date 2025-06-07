package com.alurachallenger.challenge_literalura.model;
import com.alurachallenger.challenge_literalura.service.DatoLibro;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Integer descargas;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autores",
            joinColumns = @JoinColumn(name = "libros_id"),
            inverseJoinColumns = @JoinColumn(name = "autores_id")
    )
    private List<Autor> autores;


    public Libro() {}

    public Libro(DatoLibro datoLibro , List<Autor> autoresAsociados) {
        this.titulo = datoLibro.titulo();
        this.descargas = datoLibro.descargas();
        this.idiomas = datoLibro.idiomas();
        this.autores = autoresAsociados;
        for (Autor autor : this.autores) {
            autor.getLibros().add(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descargas=" + descargas +
                ", idiomas=" + idiomas +
                ", autores=" + autores +
                '}';
    }
}
