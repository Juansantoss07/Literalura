package com.literalura.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(
        @JsonAlias("title") String titulo,
        @JsonAlias("download_count") Integer numeroDownloads,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("authors") List<DadosAutor> autores
) {
}
