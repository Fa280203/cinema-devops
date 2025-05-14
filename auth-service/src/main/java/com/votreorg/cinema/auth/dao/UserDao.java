package com.votreorg.cinema.auth.dao;

import com.votreorg.cinema.auth.entity.User;
import com.votreorg.cinema.auth.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class UserDao {

    /** Cherche un utilisateur par son username. */
    public Optional<User> findByUsername(String username) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> q = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :uname", User.class);
            q.setParameter("uname", username);
            return q.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }

    /** Sauvegarde ou met à jour un utilisateur. */
    public User save(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            User merged = em.merge(user);
            em.getTransaction().commit();
            return merged;
        } finally {
            em.close();
        }
    }

    /** Supprime un utilisateur existant. */
    public void delete(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            User toRemove = em.contains(user) ? user : em.merge(user);
            em.remove(toRemove);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
