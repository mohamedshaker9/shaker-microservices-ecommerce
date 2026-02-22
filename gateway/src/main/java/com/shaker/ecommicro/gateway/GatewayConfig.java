package com.shaker.ecommicro.gateway;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Inventory service route
                .route("inventory", r -> r
                        .path("/api/v1/public/products/**", "/api/v1/admin/products/**", "/api/v1/public/categories")
                        .uri("lb://inventory"))

                // AI Chat service route
                .route("aichat", r -> r
                        .path("/api/v1/chat/**")
                        .uri("lb://aichat"))

                // Order service route
                .route("order", r -> r
                        .path("/api/v1/carts/**", "/api/v1/orders/**")
                        .uri("lb://order"))

                // Eureka web UI route
                .route("eureka-web", r -> r
                        .path("/eureka/web")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))

                // Eureka resources route
                .route("eureka-resources", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))

                .build();
    }
}
