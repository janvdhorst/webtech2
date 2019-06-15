package de.ls5.wt2.auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

@Path("/auth/{a:session|basic|jwt}/profile")
public class UserREST {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getProfile() {

        final Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok(subject.getPrincipal().toString()).build();
    }
}
