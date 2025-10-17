package com.CptFranck.ApiGateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

public class WebConfig {

    private static final List<String> HEADERS_ACCEPTED = List.of(
            "Authorization",
            "Content-Type",
            "Accept",
            "Origin",
            "X-Requested-With"
    );
    private static final List<String> METHODS_REST = List.of("GET", "POST");

    private static String[] ALLOWED_ORIGINS;

    public WebConfig(@Value("${security.cors.allowed-origins}") String[] allowedOrigins) {
        ALLOWED_ORIGINS = allowedOrigins;
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOriginPattern(Arrays.toString(ALLOWED_ORIGINS));
        corsConfig.addAllowedMethod(String.valueOf(METHODS_REST));
        corsConfig.addAllowedHeader(String.valueOf(HEADERS_ACCEPTED));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
