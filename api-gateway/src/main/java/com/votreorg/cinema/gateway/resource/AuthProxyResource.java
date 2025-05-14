package com.votreorg.cinema.gateway.resource;

import com.votreorg.cinema.gateway.dto.Credentials;
import com.votreorg.cinema.gateway.util.ClientUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthProxyResource {

    private final String authBase;

    // TRACE au chargement de la classe
    static {
        System.out.println("[DEBUG] AuthProxyResource loaded");
    }

    public AuthProxyResource() {
        System.out.println("[DEBUG] AuthProxyResource constructor");
        String url;
        try {
            url = ClientUtil.getProperty("auth.url");
        } catch (Exception e) {
            url = "http://auth:8080/auth-service/webapi";
        }
        System.out.println("[DEBUG] AuthProxyResource.authBase = " + url);
        this.authBase = url;
    }

    @POST @Path("/register")
    public Response register(Credentials creds) {
        System.out.println("[DEBUG] AuthProxyResource.register() creds=" + creds.getUsername());
        try {
            return ClientUtil.getClient()
                    .target(authBase + "/auth/register")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(creds));
        } catch (Exception e) {
            // imprime la stacktrace dans catalina.out / stdout
            e.printStackTrace();
            // renvoie un 502 avec le message d’erreur pour le voir dans curl
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity("Upstream error: " + e.getMessage())
                    .build();
        }
    }


    @POST
    @Path("/login")
    public Response login(Credentials creds) {
        System.out.println("[DEBUG] AuthProxyResource.login() creds=" + creds.getUsername());
        return ClientUtil.getClient()
                .target(authBase + "/auth/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(creds));
    }
}
