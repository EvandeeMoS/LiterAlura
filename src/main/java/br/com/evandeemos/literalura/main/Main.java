package br.com.evandeemos.literalura.main;

import br.com.evandeemos.literalura.dto.GutendexResWrapper;
import br.com.evandeemos.literalura.model.Language;
import br.com.evandeemos.literalura.service.ApiConsumer;
import br.com.evandeemos.literalura.service.JsonParser;

import java.util.Scanner;

public class Main {

    ApiConsumer httpSerice = new ApiConsumer();
    JsonParser jsonParser = new JsonParser();
    Scanner input = new Scanner(System.in);

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
        System.out.println(jsonParser.fromJson(httpSerice.findBook(bookTitle), GutendexResWrapper.class).books().getFirst());
    }

    public void listBooks() {
        System.out.println("Coletando dados...");
        System.out.println("livro");
    }

    public void listBooksByLanguage(Language language) {
        System.out.println("Coletando dados...");
        System.out.println("Livros coletados escritos em " + language.getLanguageName());
    }

    public void listAuthors() {
        System.out.println("Coletando dados...");
        System.out.println("Autores");
    }

    public void listAuthorsByPeriod(Integer begin, Integer end) {
        System.out.println("Coletando dados...");
        System.out.println("Autores do periodo");
    }

    private int convertYear(String year) {
        return Integer.valueOf(year.contains("AC") || year.contains("BC") ?
                "-" + year.trim().replaceAll("\\D+", "") : year);
    }
}
