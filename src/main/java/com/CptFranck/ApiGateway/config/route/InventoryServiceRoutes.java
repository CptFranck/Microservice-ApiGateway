package com.CptFranck.ApiGateway.config.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryServiceRoutes {

    private final String inventoryServiceUrl;

    public InventoryServiceRoutes(@Value("${app.backend.inventory-service}") String inventoryServiceUrl) {
        this.inventoryServiceUrl = inventoryServiceUrl;
    }

    @Bean
    public RouteLocator inventoryRoutes(RouteLocatorBuilder builder) {
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
                .build();
    }
}