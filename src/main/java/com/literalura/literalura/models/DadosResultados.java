package com.literalura.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosResultados(
        @JsonAlias("count") Integer quantidade,
        @JsonAlias("results") List<DadosLivros> resultados
) {
}
