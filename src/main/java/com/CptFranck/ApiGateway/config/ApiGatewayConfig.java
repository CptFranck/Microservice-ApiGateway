package com.CptFranck.ApiGateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Value("${app.backend.inventory-service}")
    private String inventoryServiceUrl;

    @Value("${app.backend.booking-service}")
    private String bookingServiceUrl;

    @Value("${app.backend.notification-service}")
    private String notificationServiceUrl;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("inventory-venue", r -> r
                        .path("/api/v1/inventory/venue/{venueId}")
                        .uri(inventoryServiceUrl + "/api/v1/inventory/venue/"))
                .route("inventory-event", r -> r
                        .path("/api/v1/inventory/event/{eventId}")
                        .uri(inventoryServiceUrl + "/api/v1/inventory/event/"))
                .route("inventory-events", r -> r
                        .path("/api/v1/inventory/events")
                        .uri(inventoryServiceUrl + "/api/v1/inventory/events"))
                .route("inventory-api-docs", r -> r
                        .path("/docs/inventory-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/docs/inventory-service/v3/api-docs", "/v3/api-docs"))
                        .uri(inventoryServiceUrl))

                .route("booking-service", r -> r
                        .path("/api/v1/booking")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("bookingServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri(bookingServiceUrl))
                .route("booking-api-docs", r -> r
                        .path("/docs/booking-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/docs/booking-service/v3/api-docs", "/v3/api-docs"))
                        .uri(bookingServiceUrl))

                .route("fallback-route", r -> r
                        .path("/fallbackRoute")
                        .uri("forward:/fallbackRoute")) // Forward to local fallback handler

                .route("notification-ws", r -> r
                        .path("/ws-notification/**")
                        .uri("ws://" + notificationServiceUrl)) // WebSocket proxy

                .build();
    }
}
