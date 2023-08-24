package com.ffs.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffs.util.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public JsonUtil jsonUtil() {
        return new JsonUtil(new ObjectMapper());
    }
}
