//package com.votreorg.cinema.gateway.resource;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import com.votreorg.cinema.gateway.filter.JwtAuthFilter;
//import javax.inject.Inject;
//import javax.ws.rs.client.Entity;
//
//
//@Path("/cinemas")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class CinemaProxyResource {
//    @Inject
//    JwtAuthFilter jwtAuthFilter;
//
//    private static final String TARGET_URL = "http://cinema:8091/cinema-service/cinemas";
//    private final Client client = ClientBuilder.newClient();
//
//    @GET
//    public Response listAll() {
//        // JWT validation
//        jwtAuthFilter.filter();
//        return client.target(TARGET_URL)
//                .request()
//                .get();
//    }
//
//    @GET
//    @Path("/{id}")
//    public Response getById(@PathParam("id") String id) {
//        jwtAuthFilter.filter();
//        return client.target(TARGET_URL + "/" + id)
//                .request()
//                .get();
//    }
//
//    @POST
//    public Response create(String cinemaJson) {
//        jwtAuthFilter.filter();
//        return client.target(TARGET_URL)
//                .request()
//                .post(javax.ws.rs.client.Entity.entity(cinemaJson, MediaType.APPLICATION_JSON));
//    }
//
//    @PUT
//    @Path("/{id}")
//    public Response update(@PathParam("id") String id, String cinemaJson) {
//        jwtAuthFilter.filter();
//        return client.target(TARGET_URL + "/" + id)
//                .request()
//                .put(javax.ws.rs.client.Entity.entity(cinemaJson, MediaType.APPLICATION_JSON));
//    }
//
//    @DELETE
//    @Path("/{id}")
//    public Response delete(@PathParam("id") String id) {
//        jwtAuthFilter.filter();
//        return client.target(TARGET_URL + "/" + id)
//                .request()
//                .delete();
//    }
//}