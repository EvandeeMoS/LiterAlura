package br.com.evandeemos.literalura.main;

import br.com.evandeemos.literalura.service.ApiConsumer;

public class Main {

    ApiConsumer httpSerice = new ApiConsumer();

    public void run() {
        System.out.println(httpSerice.fetch("https://gutendex.com/books/?search=dom%20casmurro"));

    }
}
