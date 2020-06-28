package com.jmendoza.wallet.configuration.beans;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CreateBean {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
