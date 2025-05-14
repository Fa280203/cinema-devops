package com.votreorg.cinema.auth.controller;

import com.votreorg.cinema.auth.dto.Credentials;
import com.votreorg.cinema.auth.dto.TokenResponse;
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
public class AuthResource {
    private final UserDao userDao = new UserDao();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials creds) {
        Optional<User> opt = userDao.findByUsername(creds.getUsername());
        if (opt.isEmpty() || !opt.get().getPasswordHash().equals(creds.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials").build();
        }
        String token = JwtUtil.issueToken(creds.getUsername());
        return Response.ok(new TokenResponse(token)).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Credentials creds) {
        if (userDao.findByUsername(creds.getUsername()).isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("User already exists").build();
        }
        User newUser = new User();
        newUser.setUsername(creds.getUsername());
        newUser.setPasswordHash(creds.getPassword());
        userDao.save(newUser);
        String token = JwtUtil.issueToken(creds.getUsername());
        return Response.status(Response.Status.CREATED)
                .entity(new TokenResponse(token)).build();
    }