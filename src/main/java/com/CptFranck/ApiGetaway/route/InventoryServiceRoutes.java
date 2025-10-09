package com.CptFranck.ApiGetaway.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class InventoryServiceRoutes {

    private static String INVENTORY_SERVICE_URL;

    public InventoryServiceRoutes(@Value("${app.backend.inventory-service}") String bookingServiceUrl) {
        INVENTORY_SERVICE_URL = bookingServiceUrl;
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryRoutes() {
        return GatewayRouterFunctions.route("inventory-service")
                .GET(RequestPredicates.path("/api/v1/inventory/venue/{venueId}"), http())
                .before(uri(INVENTORY_SERVICE_URL + "api/v1/inventory/venue/"))
                .GET(RequestPredicates.path("/api/v1/inventory/event/{eventId}"), http())
                .before(uri(INVENTORY_SERVICE_URL + "api/v1/inventory/event/"))
                .GET(RequestPredicates.path("/api/v1/inventory/events"), http())
                .before(uri(INVENTORY_SERVICE_URL + "/api/v1/inventory/events"))
                .build();
    }
}