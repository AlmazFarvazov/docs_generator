package ru.itis.javalab.afarvazov.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.itis.javalab.afarvazov.dto.UserTemplateDto;

public class JsonUtil {

    public static String templateToJson(UserTemplateDto dto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return  objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Can't convert template to json");
        }
    }

}
