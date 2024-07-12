package com.literalura.literalura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(unique = true)
    private String titulo;
    private Integer downloads;
    private String idioma;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    public Livro(){}

    public Livro(String titulo, Integer downloads, String idioma, Autor autor) {
        this.titulo = titulo;
        this.downloads = downloads;
        this.idioma = idioma;
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
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
                "Idioma: " + idioma + "\n" +
                "Autor: " + autor  +
                "--------------------------" + "\n";
    }
}
