package com.literalura.literalura.repository;

import com.literalura.literalura.models.Autor;
import com.literalura.literalura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT DISTINCT l.autor FROM Livro l")
    List<Autor> findDistinctAutores();

    @Query("SELECT a FROM Autor a " +
            "WHERE a.anoNascimento <= :anoFalecimento " +  // Autor nasceu antes ou no ano de falecimento máximo do intervalo
            "AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :anoNascimento)")  // Autor não faleceu ou faleceu após ou no ano de nascimento mínimo do intervalo
    List<Autor> encontrarAutoresVivosPorAno(Integer anoNascimento, Integer anoFalecimento);

    @Query("SELECT l FROM Livro l WHERE l.idioma ILIKE :idioma")
    List<Livro> encontrarLivrosPorIdioma(String idioma);

}
