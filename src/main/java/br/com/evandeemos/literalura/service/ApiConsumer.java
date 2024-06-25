package br.com.evandeemos.literalura.service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;

public class ApiConsumer {
    public String fetch(String url) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return response.body();
        }
        catch (InterruptedException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String findBook(String title) {
        String url = "https://gutendex.com/books/?search=" + title.toLowerCase().replace(" ", "%20");
        return fetch(url);
    }
}
