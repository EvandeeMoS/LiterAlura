package br.com.evandeemos.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDto (
        String name,
        @JsonAlias("birth_year")
        String birthYear,
        @JsonAlias("death_year")
        String deathYear
) {
}
