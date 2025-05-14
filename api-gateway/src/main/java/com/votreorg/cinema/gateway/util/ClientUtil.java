package com.votreorg.cinema.gateway.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ClientUtil {
    private static final Client client = ClientBuilder.newBuilder().build();

    public static Client getClient() {
        return client;
    }
}
