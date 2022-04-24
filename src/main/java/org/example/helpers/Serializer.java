package org.example.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;

public class Serializer {

    ObjectMapper mapper;
    ObjectMapper yamlMapper;

    public Serializer() {
        setUpObjectMappers();
    }

    private void setUpObjectMappers() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        yamlMapper = new ObjectMapper(new YAMLFactory());
    }

    public <T> T deserializeJson(String json, Class<T> target) {

        try {
            return mapper.readValue(json, target);
        } catch (JsonProcessingException ex) {
            Assertions.fail(ex.getMessage());
        }
        Assertions.fail("Unable to deserialize object");
        return null;
    }

    public <T> T deserializeYaml(File yaml, Class<T> target) {

        Object o = null;
        try {
            return yamlMapper.readValue(yaml, target);
        } catch (IOException ex) {
            Assertions.fail(ex.getMessage());
        }

        Assertions.fail("Unable to deserialize object");
        return null;
    }

    public String serialize(Object contentClass) {
        String json = "";
        try {
            json = mapper.writeValueAsString(contentClass);
        } catch (JsonProcessingException ex) {
            Assertions.fail(ex.getMessage());
        }
        Assertions.assertNotEquals("", json, "Unable to deserialize object");
        return json;
    }
}
