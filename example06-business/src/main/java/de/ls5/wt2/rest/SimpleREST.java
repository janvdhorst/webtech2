package de.ls5.wt2.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("test")
public class SimpleREST {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String methodNamesDoNoMatter() {
        return "Hello World";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String reallyTheyDontMatter() {
        return "{\"hello\": \"world\"}";
    }

    @POST
    @Path("withPath")
    @Produces(MediaType.TEXT_PLAIN)
    public String didIAlreadySayTheyDontMatter() {
        return "Goodbye world";
    }
}
