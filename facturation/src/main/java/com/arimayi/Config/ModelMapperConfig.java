package com.arimayi.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    //ca c'est pour le mapping des entités et dto
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}