package com.votreorg.cinema.gateway.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.votreorg.cinema.gateway.util.ClientUtil;

@Path("/cinemas")
public class CinemaProxyResource {
    private static final String BASE = "http://cinema:8091/cinema-service/cinemas";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return ClientUtil.getClient()
                .target(BASE)
                .request()
                .get();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String body) {
        return ClientUtil.getClient()
                .target(BASE)
                .request()
                .post(Entity.json(body));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        return ClientUtil.getClient()
                .target(BASE + "/" + id)
                .request()
                .get();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, String body) {
        return ClientUtil.getClient()
                .target(BASE + "/" + id)
                .request()
                .put(Entity.json(body));
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id) {
        return ClientUtil.getClient()
                .target(BASE + "/" + id)
                .request()
                .delete();
    }