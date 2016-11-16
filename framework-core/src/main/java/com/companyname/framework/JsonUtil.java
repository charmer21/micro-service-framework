package com.companyname.framework;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;

public final class JsonUtil {
    private static ObjectMapper mapper;

    static {
        mapper = generateMapper(JsonInclude.Include.ALWAYS);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    private static ObjectMapper generateMapper(JsonInclude.Include include) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(include);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper;
    }

    public static <T> T parse(String jsonStr, Class<T> clazz) throws IOException {
        String safeJsonStr = jsonStr.replace("\\", "/");
        return clazz.equals(String.class) ? (T) jsonStr : mapper.readValue(safeJsonStr, clazz);
    }

    public static <T> String toString(T entity) throws IOException {
        return entity instanceof String ? (String) entity : mapper.writeValueAsString(entity);
    }


}
