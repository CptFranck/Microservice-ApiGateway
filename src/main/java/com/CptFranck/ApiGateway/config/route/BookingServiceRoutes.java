package com.CptFranck.ApiGateway.config.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;


import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class BookingServiceRoutes {

    private static String BOOKING_SERVICE_URL;

    public BookingServiceRoutes(@Value("${app.backend.booking-service}") String bookingServiceUrl) {
        BOOKING_SERVICE_URL = bookingServiceUrl;
    }

    @Bean
    public RouterFunction<ServerResponse> bookingRoutes() {
        return GatewayRouterFunctions.route("booking-service")
                .POST(RequestPredicates.path("/api/v1/booking"), http())
                .before(uri(BOOKING_SERVICE_URL))
                .filter(setPath("/api/v1/booking"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("bookingServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return GatewayRouterFunctions.route("fallbackRoute")
                .POST("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE) .body("Booking service is down"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceApiDocs() {
        return GatewayRouterFunctions.route("booking-service-api-docs")
                .route(RequestPredicates.path("/docs/booking-service/v3/api-docs"), http())
                .before(uri(BOOKING_SERVICE_URL))
                .filter(setPath("/v3/api-docs"))
                .build();
    }
}