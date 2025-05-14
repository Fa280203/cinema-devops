package com.votreorg.cinema.auth.controller;

import com.votreorg.cinema.auth.dto.Credentials;
import com.votreorg.cinema.auth.dto.TokenResponse;
import com.votreorg.cinema.auth.dto.UserDTO;
import com.votreorg.cinema.auth.dao.UserDao;
import com.votreorg.cinema.auth.entity.User;
import com.votreorg.cinema.auth.util.JwtUtil;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final UserDao userDao = new UserDao();

    @POST
    @Path("/register")
    public Response register(Credentials creds) {
        // 1) Vérifie que l'utilisateur n'existe pas déjà
        if (userDao.findByUsername(creds.getUsername()).isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("User already exists")
                    .build();
        }

        // 2) Hash du mot de passe et persistance
        User u = new User();
        u.setUsername(creds.getUsername());
        u.setPasswordHash(JwtUtil.hashPassword(creds.getPassword()));
        User saved = userDao.save(u);

        // 3) Génération d'un JWT
        String token = JwtUtil.issueToken(saved.getUsername());

        // 4) On renvoie le UserDTO + le token
        return Response.status(Response.Status.CREATED)
                .entity(new TokenResponse(token, UserDTO.fromEntity(saved)))
                .build();
    }

    @POST
    @Path("/login")
    public Response login(Credentials creds) {
        // 1) Recherche de l'utilisateur
        Optional<User> opt = userDao.findByUsername(creds.getUsername());
        if (opt.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials")
                    .build();
        }

        User user = opt.get();
        // 2) Vérification du mot de passe via hash
        if (!JwtUtil.verifyPassword(creds.getPassword(), user.getPasswordHash())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials")
                    .build();
        }

        // 3) Issue du token
        String token = JwtUtil.issueToken(user.getUsername());

        // 4) Réponse 200 avec token et infos utilisateur
        return Response.ok(new TokenResponse(token, UserDTO.fromEntity(user))).build();
    }
}
