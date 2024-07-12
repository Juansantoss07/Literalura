package com.literalura.literalura.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(unique = true)
    private String titulo;
    private Integer downloads;
    private List<String> idiomas;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    public Livro(){}

    public Livro(String titulo, Integer downloads, List<String> idiomas, Autor autor) {
        this.titulo = titulo;
        this.downloads = downloads;
        this.idiomas = idiomas;
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return
                "---------- LIVRO ----------" + "\n" +
                "Titulo Do livro: " + titulo + "\n" +
                "Downloads: " + downloads + "\n" +
                "Idiomas: " + idiomas + "\n" +
                "Autor: " + autor  + "\n" +
                "--------------------------";
    }
}
