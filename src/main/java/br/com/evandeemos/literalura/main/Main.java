package br.com.evandeemos.literalura.main;

import br.com.evandeemos.literalura.dto.GutendexResWrapper;
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
                  4 - Consultar Autor
                  5 - Listar autores por período
                
                  0 - Sair da aplicação
                ===================================
                """);

            int option = Integer.valueOf(input.nextLine());

            switch (option) {
                case 1 -> findBook();
                case 2 -> listBooks();
                case 3 -> {
                    System.out.println("""
                            Insira o idioma desejado:
                            1 - Português;
                            2 - Inglês;
                            3 - Françês;""");
                    option = Integer.valueOf(input.nextLine());
                    try {
                        listBooksByLanguage(Language.fromLanguageValue(option));
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> listAuthors();
                case 5 -> {
                    try {
                        System.out.println("Insira o ano inícial do período: ");
                        System.out.println("modelo: 1999 ou 300AC ou 800BC");
                        int begin = convertYear(input.nextLine());
                        System.out.println("Insira o ano final do período: ");
                        int end = convertYear(input.nextLine());
                        listAuthorsByPeriod(begin, end);
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Por favor, insíra um ano válido");
                    }
                }
                case 0 -> {
                    running = false;
                    continue;
                }
                default -> System.out.println("Escolha uma opção válida!");
            }
            System.out.println("voltando para menu...");
        }
    }

    public void findBook() {
        System.out.println("Escreva o título do livro: ");
        String bookTitle = input.nextLine();
        System.out.println("Buscando...");
        Book book = new Book(jsonParser.fromJson(httpSerice.findBook(bookTitle), GutendexResWrapper.class).books().getFirst());
        System.out.println(book);
        authorRepository.save(book.getAuthor());
        bookRepository.save(book);
    }

    public void listBooks() {
        System.out.println("Coletando dados...");
        List<Book> Books = bookRepository.findAll();
        if (!Books.isEmpty()) {
            Books.forEach(System.out::println);
        }
        else {
            System.out.println("Nenhum livro foi pesquisado...");
        }
    }

    public void listBooksByLanguage(Language language) {
        System.out.println("Coletando dados...");
        bookRepository.findAllByLanguage(language).forEach(System.out::println);
    }

    public void listAuthors() {
        System.out.println("Coletando dados...");
        authorRepository.findAll().forEach(System.out::println);
    }

    public void listAuthorsByPeriod(Integer begin, Integer end) {
        System.out.println("Coletando dados...");
        authorRepository.findAuthorsAliveInPeriod(begin, end)
                .forEach(System.out::println);
    }

    private int convertYear(String year) {
        String yearUpper = year.toUpperCase();
        return Integer.valueOf(yearUpper.contains("AC") || yearUpper.contains("BC") ?
                "-" + yearUpper.trim().replaceAll("\\D+", "") : yearUpper);
    }
}
