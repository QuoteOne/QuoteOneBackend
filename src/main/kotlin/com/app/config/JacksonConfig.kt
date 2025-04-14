package com.app.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class JacksonConfig {
    @Bean("Yaml")
    fun yamlObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper(YAMLFactory())
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        return objectMapper
    }

    @Bean
    @Primary
    fun jsonObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        return objectMapper
    }

}