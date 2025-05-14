package com.votreorg.cinema.auth.dto;

import java.time.Instant;
import com.votreorg.cinema.auth.entity.User;

/**
 * Data Transfer Object pour renvoyer les infos d'un utilisateur sans exposer son passwordHash.
 */
public class UserDTO {
    private Integer id;
    private String username;
    private String role;
    private Instant createdAt;

    public UserDTO() {}

    public UserDTO(Integer id, String username, String role, Instant createdAt) {
        this.id        = id;
        this.username  = username;
        this.role      = role;
        this.createdAt = createdAt;
    }

    /**
     * Mapping depuis l'entité User
     */
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole().name(),   // convert enum to String
                user.getCreatedAt()
        );
    }

    // Getters / setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
