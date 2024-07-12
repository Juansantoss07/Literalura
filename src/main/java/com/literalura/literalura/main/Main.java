package com.literalura.literalura.main;

import com.literalura.literalura.models.*;
import com.literalura.literalura.repository.LivroRepository;
import com.literalura.literalura.service.ConsumoAPI;
import com.literalura.literalura.service.ConverteDados;

import java.util.List;
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
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLivrosEmUmIdioma();
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
        Livro livro = new Livro(dadoLivro.titulo(), dadoLivro.numeroDownloads(), dadoLivro.idiomas().get(0), autor);

        repositorio.save(livro);

        System.out.println("o Livro " + livro.getTitulo() + " foi registrado no banco com suas informações.");
    }

    private void listarLivrosRegistrados(){
        List<Livro> livros = repositorio.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados(){
        List<Autor> autores = repositorio.findDistinctAutores();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosPorAno(){
        System.out.println("Digite o primeiro ano que deseja pesquisar (A partir de):");
        var anoInicio = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Agora, digite o último ano (até):");
        var anoFinal = leitura.nextInt();
        leitura.nextLine();
        List<Autor> autoresVivos = repositorio.encontrarAutoresVivosPorAno(anoInicio, anoFinal);
        autoresVivos.forEach(System.out::println);
    }

    private void listarLivrosEmUmIdioma(){
        System.out.println(""" 
                        Insira o idioma para realizar a busca:
                        es - espanhol
                        en - inglês
                        fr - francês
                        pt - português
                """
        );

        var idioma = leitura.nextLine();

        List<Livro> livrosPorIdioma = repositorio.encontrarLivrosPorIdioma(idioma);
        livrosPorIdioma.forEach(System.out::println);
    }
}
