package br.com.evandeemos.literalura.model;

import br.com.evandeemos.literalura.dto.BookDto;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;
    private List<String> subjects;
    @Enumerated(EnumType.STRING)
    private Language language;
    private String copyright;
    private int downloadCount;

    public Book(){}

    public Book(String title, Author author, List<String> subjects, String language, String copyright, int downloadCount) {
        this.title = title;
        this.author = author;
        this.subjects = subjects;
        this.language = Language.valueOf(language.toUpperCase());
        this.copyright = copyright;
        this.downloadCount = downloadCount;
    }

    public Book(BookDto bookDto) {
        this.title = bookDto.title();
        this.author = new Author(bookDto.authors().getFirst());
        this.subjects = bookDto.subjects();
        this.language = bookDto.languages().stream().limit(1)
                .map(lanStr -> Language.valueOf(lanStr.toUpperCase())).findFirst().orElse(null);
        this.copyright = bookDto.copyright();
        this.downloadCount = bookDto.downloadCount();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "Livro [" + "\n" + "  Titulo: " + title + "\n" +
                "  Autor: " + author.getName() + ".  Idioma: " + language + "\n" +
                "  Downloads: " + downloadCount + "\n" + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                downloadCount == book.downloadCount &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(subjects, book.subjects) &&
                Objects.equals(language, book.language) &&
                Objects.equals(copyright, book.copyright);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, subjects, language, copyright, downloadCount);
    }
}
