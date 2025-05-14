package com.votreorg.cinema.gateway.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Utilitaire pour charger la configuration et fournir un client JAX-RS configuré.
 */
public class ClientUtil {
    private static final Properties props = new Properties();
    private static final String CONFIG = "application.properties";

    static {
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(CONFIG)) {
            if (in == null) {
                throw new RuntimeException("Config file '" + CONFIG + "' not found on classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    /** Renvoie un Client JAX-RS configuré (Jackson…) */
    public static Client getClient() {
        return ClientBuilder.newClient().register(JacksonFeature.class);
    }

    /** Lit une clé depuis application.properties sur le classpath */
    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
