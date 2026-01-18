package com.curso.react.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**").allowedMethods("*") //soporte para todos los metodos HTTP
      .allowedOrigins("*")  //Soporte para todos los origene
      .allowedHeaders("*");  //soporte para todos los headers

    }
}
