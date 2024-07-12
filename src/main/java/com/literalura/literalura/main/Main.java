package com.literalura.literalura.main;

import com.literalura.literalura.models.*;
import com.literalura.literalura.repository.LivroRepository;
import com.literalura.literalura.service.ConsumoAPI;
import com.literalura.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class Main {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private final String BASE_URL = "https://gutendex.com/books/";
    private String json = consumo.obterDados(BASE_URL);
    private ConverteDados conversor = new ConverteDados();
    private LivroRepository repositorio;

    public Main(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibirMenu(){
        var opcao = -1;
        while (opcao != 0){
            var menu = """
                        ********** LITERALURA **********
                        
                        Escolha o número de sua opção:
                        1- Buscar livros pelo título
                        2- listar livros registrados
                        3- listar autores registrados
                        4- listar autores vivos em um determinado ano 
                        5- listar livros em um determinado idioma
                        
                        0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 1:
                    buscarLivrosPeloTitulo();
                    break;
            }
        }
    }

    private void buscarLivrosPeloTitulo() {
        System.out.println("Digite o nome do livro que você deseja procurar:");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(BASE_URL + "?search=" + nomeLivro.replace(" ", "%20").toLowerCase());
        DadosResultados dados = conversor.obterDados(json, DadosResultados.class);
        DadosLivros dadoLivro = dados.resultados().get(0);
        DadosAutor dadoAutor = dadoLivro.autores().get(0);

        Autor autor = new Autor(dadoAutor.nome(), dadoAutor.anoNascimento(), dadoAutor.anoFalecimento());
        Livro livro = new Livro(dadoLivro.titulo(), dadoLivro.numeroDownloads(), dadoLivro.idiomas(), autor);

        repositorio.save(livro);

        System.out.println("o Livro " + livro.getTitulo() + " foi registrado no banco com suas informações.");
    }

}
