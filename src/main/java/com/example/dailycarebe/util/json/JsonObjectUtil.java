package com.example.dailycarebe.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public class JsonObjectUtil {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

    private static ObjectMapper objectMapper;

    static {
        objectMapper = makeDefaultObjectMapper();
    }

    public static ObjectMapper makeDefaultObjectMapper() {
//      JavaTimeModule javaTimeModule = new JavaTimeModule();
//      javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
//      javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
      ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
                .findModulesViaServiceLoader(true)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .modulesToInstall(new JavaTimeModule())
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer())
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer())
                .deserializerByType(LocalDate.class, new LocalDateDeserializer())
                .dateFormat(DATE_FORMAT)
                .build();

        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

        return objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @SneakyThrows(IOException.class)
    public static String toJson(Object object) {
        if (object == null)
            return null;
        return getObjectMapper().writeValueAsString(object);
    }

    public static Map toMap(Object object) {
        if (object == null)
            return null;
        return getObjectMapper().convertValue(object, Map.class);
    }

    public static <T> T fromMap(Map map, Class<T> cls) {
        if (map == null)
            return null;
        return getObjectMapper().convertValue(map, cls);
    }

    @SneakyThrows(IOException.class)
    public static <T> T fromJson(String jsonStr, Class<T> cls) {
        return getObjectMapper().readValue(jsonStr, cls);
    }

    @SneakyThrows(IOException.class)
    public static <T> T fromJson(String jsonStr, TypeReference<T> typeReference) {
        return getObjectMapper().readValue(jsonStr, typeReference);
    }

    @SneakyThrows(IOException.class)
    public static JsonNode fromJson(String json) {
        return getObjectMapper().readTree(json);
    }

    @SneakyThrows(IOException.class)
    public static <T extends Collection> T fromJson(String jsonStr, CollectionType collectionType) {
        return getObjectMapper().readValue(jsonStr, collectionType);
    }

    @SneakyThrows(JsonProcessingException.class)
    public static String toPrettyJson(String json) {
        if (json == null)
            return "";
        Object jsonObject = fromJson(json, Object.class);
        return getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }

    public static String toJson(Object object, String defaultValue) {
        String json = null;
        try {
            json = toJson(object);
        } catch (Exception e) {
        }
        return json != null ? json : defaultValue;
    }

    public static <T> T fromJson(String jsonStr, Class<T> cls, T defaultValue) {
        try {
            return fromJson(jsonStr, cls);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static <T> T fromJson(String jsonStr, TypeReference<T> typeReference, T defaultValue) {
        try {
            return fromJson(jsonStr, typeReference);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static <T extends Collection> T fromJson(String jsonStr, Class<T> cls, Class contentCls) {
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(cls, contentCls);
        return fromJson(jsonStr, javaType);
    }

    public static JsonNode fromJson(String json, JsonNode defaultValue) throws Exception {
        try {
            return fromJson(json);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static <T extends Collection> T fromJson(String jsonStr, CollectionType collectionType, T defaultValue) {
        try {
            return fromJson(jsonStr, collectionType);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String toPrettyJson(String json, String defaultValue) {
        try {
            return toPrettyJson(json);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}

