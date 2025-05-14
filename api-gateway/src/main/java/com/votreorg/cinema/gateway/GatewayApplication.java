package com.votreorg.cinema.gateway;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.votreorg.cinema.gateway.filter.JwtAuthFilter;

public class GatewayApplication extends ResourceConfig {
    public GatewayApplication() {
        // Scanner les ressources et filtres
        packages("com.votreorg.cinema.gateway");
        register(JacksonFeature.class);
        register(JwtAuthFilter.class);
        register(com.votreorg.cinema.gateway.util.JacksonConfig.class);
    }
}
