package br.com.evandeemos.literalura.main;

import br.com.evandeemos.literalura.dto.GutendexResWrapper;
import br.com.evandeemos.literalura.model.Author;
import br.com.evandeemos.literalura.model.Book;
import br.com.evandeemos.literalura.model.Language;
import br.com.evandeemos.literalura.repository.AuthorRepository;
import br.com.evandeemos.literalura.repository.BookRepository;
import br.com.evandeemos.literalura.service.ApiConsumer;
import br.com.evandeemos.literalura.service.JsonParser;

import java.util.List;
import java.util.Scanner;

public class Main {

    private ApiConsumer httpSerice = new ApiConsumer();
    private JsonParser jsonParser = new JsonParser();
    private Scanner input = new Scanner(System.in);
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Main(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void run() {
        menu();
    }

    public void menu() {
        boolean running = true;
        while (running) {
            System.out.println("""
                ===================================
                  Menu
                ===================================
                  1 - Registrar Livro
                  2 - Consultar Livro
                  3 - Listar Livros por idioma
                  4 - Consultar Autor pelo nome
                  5 - Listar todos os autores
                  6 - Listar autores por período
                  7 - Top 10 livros mais baixados
                
                  0 - Sair da aplicação
                ===================================
                """);

            int option = Integer.valueOf(input.nextLine());

            switch (option) {
                case 1 -> findBook();
                case 2 -> listBooks();
                case 3 -> listBooksByLanguage();
                case 4 -> listAutorByName();
                case 5 -> listAuthors();
                case 6 -> listAuthorsByPeriod();
                case 7 -> listTop10BooksByDownloads();
                case 0 -> {
                    running = false;
                    continue;
                }
                default -> System.out.println("Escolha uma opção válida!");
            }
            System.out.println("voltando para menu...");
        }
    }

    private void findBook() {
        System.out.println("Escreva o título do livro: ");
        String bookTitle = input.nextLine();
        System.out.println("Buscando...");
        Book book = new Book(jsonParser.fromJson(httpSerice.findBook(bookTitle), GutendexResWrapper.class).books().getFirst());
        System.out.println(book);
        authorRepository.save(book.getAuthor());
        bookRepository.save(book);
    }

    private void listBooks() {
        System.out.println("Coletando dados...");
        List<Book> Books = bookRepository.findAll();
        if (!Books.isEmpty()) {
            Books.forEach(System.out::println);
        }
        else {
            System.out.println("Nenhum livro foi pesquisado...");
        }
    }

    private void listBooksByLanguage() {
        System.out.println("""
            Insira o idioma desejado:
            1 - Português;
            2 - Inglês;
            3 - Françês;
            """);
        try {
            int option = Integer.valueOf(input.nextLine());
            Language language = Language.fromLanguageValue(option);
            System.out.println("Coletando dados...");
            List<Book> books = bookRepository.findAllByLanguage(language);
            System.out.println("Livros escontrados no idioma " + language.getLanguageName());
            books.forEach(System.out::println);
        }
        catch (IllegalArgumentException e) {
            System.out.println("insira um valor válido");
        }
    }

    private void listTop10BooksByDownloads() {
        List<Book> books = bookRepository.findTop10ByOrderByDownloadCountDesc();
        System.out.println("Top 10 mais baixados");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + " - " + books.get(i));
        }
    }

    private void listAuthors() {
        System.out.println("Coletando dados...");
        authorRepository.findAll().forEach(System.out::println);
    }

    private void listAuthorsByPeriod() {
        try {
            System.out.println("Insira o ano inícial do período: \n" +
                    "modelo: 1999 ou 300AC ou 800BC");
            int begin = convertYear(input.nextLine());
            System.out.println("Insira o ano final do período: ");
            int end = convertYear(input.nextLine());
            System.out.println("Coletando dados...");
            List<Author> authors = authorRepository.findAuthorsAliveInPeriod(begin, end);
            System.out.println("Autores vivos no periodo de: " + (begin >= 0 ? begin : (begin * -1) + "AC") + " - " +
                    (end >= 0 ? end : (end * -1) + "AC"));
            authors.forEach(System.out::println);
        }
        catch (NumberFormatException e) {
            System.out.println("Por favor, insira um ano válido");
        }
    }

    private void listAutorByName() {
        System.out.println("Insira o nome do autor: ");
        String name = input.nextLine();
        List<Author> authors = authorRepository.findByNameContainingIgnoreCase(name);
        System.out.println("Autores encontrados com nome similar: ");
        authors.forEach(System.out::println);
    }

    private int convertYear(String year) {
        String yearUpper = year.toUpperCase();
        return Integer.valueOf(yearUpper.contains("AC") || yearUpper.contains("BC") ?
                "-" + yearUpper.trim().replaceAll("\\D+", "") : yearUpper);
    }
}
