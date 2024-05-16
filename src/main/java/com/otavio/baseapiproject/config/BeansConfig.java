package com.otavio.baseapiproject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class BeansConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.getConfiguration().setAmbiguityIgnored(true);
        return mapper;
    }
}
