package com.realestate.website.repositories;

import com.realestate.website.entities.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class PropertyLoader {

    private static final Logger log = LoggerFactory.getLogger(PropertyLoader.class);
    @Bean
    CommandLineRunner initPlayerStats(PropertyRepository repository){
        return args -> {
            if(repository.count() == 0) log.info("Preloading " + repository.save(new Property("Papalapap")));
        };
    }
}
