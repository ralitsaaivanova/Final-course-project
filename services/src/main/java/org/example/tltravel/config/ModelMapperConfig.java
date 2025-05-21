package org.example.tltravel.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper m = new ModelMapper();
        m.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return m;
    }
}
