package com.votreorg.cinema.auth.dto;

public class TokenResponse {
    private String token;
    private UserDTO user;

    public TokenResponse() {}

    public TokenResponse(String token) {
        this.token = token;
    }

    public TokenResponse(String token, UserDTO user) {
        this.token = token;
        this.user  = user;
    }

    // getters/setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
}
