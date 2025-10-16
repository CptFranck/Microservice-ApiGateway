package com.CptFranck.ApiGateway.config.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingServiceRoutes {

    private static String BOOKING_SERVICE_URL;

    public BookingServiceRoutes(@Value("${app.backend.booking-service}") String bookingServiceUrl) {
        BOOKING_SERVICE_URL = bookingServiceUrl;
    }

    @Bean
    public RouteLocator bookingRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("booking-service", r -> r
                        .path("/api/v1/booking")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("bookingServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri(BOOKING_SERVICE_URL))

                .route("booking-api-docs", r -> r
                        .path("/docs/booking-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/docs/booking-service/v3/api-docs", "/v3/api-docs"))
                        .uri(BOOKING_SERVICE_URL))

                .route("fallback-route", r -> r
                        .path("/fallbackRoute")
                        .uri("forward:/fallbackRoute"))
                .build();
    }

//    @Bean
//    public RouterFunction<ServerResponse> bookingRoutes() {
//        return GatewayRouterFunctions.route("booking-service")
//                .POST(RequestPredicates.path("/api/v1/booking"), http())
//                .before(uri(BOOKING_SERVICE_URL))
//                .filter(setPath("/api/v1/booking"))
//                .filter(CircuitBreakerFilterFunctions.circuitBreaker("bookingServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> fallbackRoute() {
//        return GatewayRouterFunctions.route("fallbackRoute")
//                .POST("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE) .body("Booking service is down"))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> bookingServiceApiDocs() {
//        return GatewayRouterFunctions.route("booking-service-api-docs")
//                .route(RequestPredicates.path("/docs/booking-service/v3/api-docs"), http())
//                .before(uri(BOOKING_SERVICE_URL))
//                .filter(setPath("/v3/api-docs"))
//                .build();
//    }
}