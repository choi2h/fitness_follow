package com.ffs.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private final ObjectMapper objectMapper;

    public JsonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T jsonToObject(String jsonValue, Class<T> classType) throws JsonProcessingException {
        return objectMapper.readValue(jsonValue, classType);
    }
}
