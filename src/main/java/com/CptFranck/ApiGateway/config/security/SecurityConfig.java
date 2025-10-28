package com.CptFranck.ApiGateway.config.security;

import com.CptFranck.ApiGateway.config.WebConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final String jwtUri;

    private final WebConfig webConfig;

    private final JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String jwkSetUri,
                          WebConfig webConfig,
                          JwtAuthConverter jwtAuthConverter) {
        this.jwtUri = jwkSetUri;
        this.webConfig = webConfig;
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(corsSpec -> corsSpec.configurationSource(webConfig.corsWebFilter()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/docs/**",
                                "/api-docs/**",
                                "/v3/api-docs/**",
                                "/ws-notification/**"
                        ).permitAll()
                        .pathMatchers(
                                "/api/v1/booking",
                                "/api/v1/inventory"
                        ).authenticated()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtSpec -> jwtSpec
                                .jwtDecoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthConverter)))
                .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwtUri).build();
    }
}
