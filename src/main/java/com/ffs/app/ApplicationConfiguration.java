package com.ffs.app;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffs.util.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public JsonUtil jsonUtil() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        objectMapper.configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true);

        return new JsonUtil(objectMapper);
    }
}
