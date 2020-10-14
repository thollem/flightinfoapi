package com.artsgard.flightinfoapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySource({"classpath:application.properties"})
public class FlightInfoApiApplication {
    
    //@Autowired
    //ErrorHandler errorHandler;

    @Value("${auth.username}")
    private String authUsername;

    @Value("${auth.apikey}")
    private String authApikey;

    public static void main(String[] args) {
        SpringApplication.run(FlightInfoApiApplication.class, args);
    }

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthentication(authUsername, authApikey).build(); //.errorHandler(errorHandler).build();
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        return new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }
}
