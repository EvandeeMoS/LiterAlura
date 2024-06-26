package br.com.evandeemos.literalura.model;

import br.com.evandeemos.literalura.dto.AuthorDto;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int birthYear;
    private int deathYear;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private List<Book> books;

    public Author(){}

    public Author(String name, int birthYear, int deathYear){
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Author(AuthorDto authorDto){
        this.name = authorDto.name();
        this.birthYear = Integer.valueOf(authorDto.birthYear());
        this.deathYear = Integer.valueOf(authorDto.deathYear());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Autor [\n" +
                "  Nome: " + name + "\n" +
                "  Ano de nascimento: " + birthYear + "\n" +
                "  Ano de falecimento: " + deathYear + "\n" +
                "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return birthYear == author.birthYear &&
                deathYear == author.deathYear &&
                Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthYear, deathYear);
    }
}
