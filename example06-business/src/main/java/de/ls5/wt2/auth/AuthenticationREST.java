package de.ls5.wt2.auth;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.nimbusds.jose.JOSEException;
import de.ls5.wt2.conf.auth.jwt.JWTLoginData;
import de.ls5.wt2.conf.auth.jwt.JWTUtil;
import javax.ws.rs.FormParam;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

@Path("jwt")
@Transactional
public class AuthenticationREST {

    @PersistenceContext
    private EntityManager entityManager;

    @Path("authenticate")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response createJWToken(@FormParam("jwt") String jwt) throws JOSEException {

        // do some proper lookup
        JWTUtil util = new JWTUtil();
        if(!util.validateToken(jwt)) {
          return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

}

