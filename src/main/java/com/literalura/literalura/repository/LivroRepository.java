package com.literalura.literalura.repository;

import com.literalura.literalura.models.Autor;
import com.literalura.literalura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT DISTINCT l.autor FROM Livro l")
    List<Autor> findDistinctAutores();
}
