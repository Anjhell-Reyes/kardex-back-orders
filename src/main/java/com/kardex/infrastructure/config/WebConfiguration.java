package com.kardex.infrastructure.config;

import com.kardex.domain.utils.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(Constants.CORS_ALLOWED_PATHS)
                .allowedOrigins(Constants.CORS_ALLOWED_ORIGIN)
                .allowedMethods(Constants.CORS_ALLOWED_METHODS);
    }
}
