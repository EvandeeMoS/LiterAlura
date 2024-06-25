package br.com.evandeemos.literalura.repository;

import br.com.evandeemos.literalura.model.Book;
import br.com.evandeemos.literalura.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByLanguage(Language language);
}
