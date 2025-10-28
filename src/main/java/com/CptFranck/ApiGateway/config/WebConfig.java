package com.CptFranck.ApiGateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebConfig {

    private final List<String> allowedOrigins;

    private final List<String> methodsRest = List.of(
            "GET",
            "POST",
            "OPTIONS"
    );

    private final List<String> headersAccepted = List.of(
            "Authorization",
            "Content-Type",
            "Accept",
            "Origin",
            "X-Requested-With"
    );

    public WebConfig(@Value("${security.cors.allowed-origins}") String[] allowedOrigins) {
        this.allowedOrigins = List.of(allowedOrigins);
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedMethods(methodsRest);
        corsConfig.setAllowedHeaders(headersAccepted);
        corsConfig.setAllowedOriginPatterns(allowedOrigins);
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }
}
