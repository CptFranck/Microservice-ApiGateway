package com.CptFranck.ApiGateway.config.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingServiceRoutes {

    private final String bookingServiceUrl;

    public BookingServiceRoutes(@Value("${app.backend.booking-service}") String bookingServiceUrl) {
        this.bookingServiceUrl = bookingServiceUrl;
    }

    @Bean
    public RouteLocator bookingRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("booking-service", r -> r
                        .path("/api/v1/booking")
                        .filters(f -> f
                                .tokenRelay()
                                .circuitBreaker(config -> config
                                    .setName("bookingServiceCircuitBreaker")
                                    .setFallbackUri("forward:/fallbackRoute")))
                        .uri(bookingServiceUrl))

                .route("booking-api-docs", r -> r
                        .path("/docs/booking-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/docs/booking-service/v3/api-docs", "/v3/api-docs"))
                        .uri(bookingServiceUrl))

                .route("fallback-route", r -> r
                        .path("/fallbackRoute")
                        .uri("forward:/fallbackRoute"))
                .build();
    }
}