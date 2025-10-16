package com.CptFranck.ApiGateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationServiceRoutes {

    private static String NOTIFICATION_SERVICE_URL;

    public NotificationServiceRoutes(@Value("${app.backend.notification-service}") String notificationServiceUrl) {
        NOTIFICATION_SERVICE_URL = notificationServiceUrl;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("notification-ws", r -> r
                        .path("/ws-notification/**")
                        .uri("ws://" + NOTIFICATION_SERVICE_URL)) // WebSocket proxy

                .build();
    }
}
