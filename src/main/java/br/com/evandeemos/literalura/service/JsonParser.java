package br.com.evandeemos.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T fromJson(String data, Class<T> tClass) {
        try {
            return mapper.readValue(data, tClass);
        }
        catch(JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public <T> T fromJson(String data, TypeReference<T> tType) {
        try {
            return mapper.readValue(data, tType);
        }
        catch(JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
