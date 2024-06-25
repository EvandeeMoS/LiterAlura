package br.com.evandeemos.literalura.model;

import java.util.Arrays;
import java.util.Optional;

public enum Language {
    PT("Português", 1),
    EN("Inglês", 2),
    FR("Francês", 3);

    String languageName;
    int languageValue;

    Language(String languageName, int languageValue) {
        this.languageName = languageName;
        this.languageValue = languageValue;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getLanguageValue() {
        return languageValue;
    }

    public void setLanguageValue(int languageValue) {
        this.languageValue = languageValue;
    }

    public static Language fromLanguageValue(int value) {
        Optional<Language> optLanguage = Arrays.stream(Language.values())
                .filter(language -> language.languageValue == value)
                .findFirst();
        if (optLanguage.isPresent()) {
            return optLanguage.get();
        }
        else {
            throw new IllegalArgumentException("Insira um valor válido");
        }
    }
}
