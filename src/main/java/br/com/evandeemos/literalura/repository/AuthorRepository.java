package br.com.evandeemos.literalura.repository;

import br.com.evandeemos.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a WHERE a.birthYear >= :begin AND a.birthYear <= :end OR a.deathYear >= :begin AND a.deathYear <= :end")
    List<Author> findAuthorsAliveInPeriod(int begin, int end);
}
