package com.github.mymvcspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanProperties {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
