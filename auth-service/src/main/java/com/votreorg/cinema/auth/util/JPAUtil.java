package com.votreorg.cinema.auth.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JPAUtil {
    private static final EntityManagerFactory emf;

    static {
        // 1. Charger db.properties
        Properties props = new Properties();
        try (InputStream in = JPAUtil.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Impossible de charger db.properties", e);
        }

        // 2. Transférer les props JDBC à JPA
        Map<String, String> jpaProps = new HashMap<>();
        jpaProps.put("jakarta.persistence.jdbc.url",      props.getProperty("jdbc.url"));
        jpaProps.put("jakarta.persistence.jdbc.user",     props.getProperty("jdbc.user"));
        jpaProps.put("jakarta.persistence.jdbc.password", props.getProperty("jdbc.password"));
        jpaProps.put("jakarta.persistence.jdbc.driver",   props.getProperty("jdbc.driver"));

        // 3. Créer l'EntityManagerFactory pour l’unité 'authPU'
        emf = Persistence.createEntityManagerFactory("authPU", jpaProps);
    }

    /** Fournit un EntityManager à usage local (transaction RESOURCE_LOCAL). */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
