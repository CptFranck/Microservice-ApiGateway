package com.CptFranck.ApiGateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebConfig {

    private static final List<String> HEADERS_ACCEPTED = List.of(
            "Authorization",
            "Content-Type",
            "Accept",
            "Origin",
            "X-Requested-With"
    );
    private static final List<String> METHODS_REST = List.of("GET","POST","OPTIONS");

    private static List<String> ALLOWED_ORIGINS;

    public WebConfig(@Value("${security.cors.allowed-origins}") String[] allowedOrigins) {
        ALLOWED_ORIGINS = List.of(allowedOrigins);
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(ALLOWED_ORIGINS);
        corsConfig.setAllowedMethods(METHODS_REST);
        corsConfig.setAllowedHeaders(HEADERS_ACCEPTED);
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
