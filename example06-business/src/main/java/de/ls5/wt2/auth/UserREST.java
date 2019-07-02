package de.ls5.wt2.auth;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import de.ls5.wt2.conf.auth.permission.ViewFirstFiveNewsItemsPermission;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import javax.transaction.Transactional;
import javax.ws.rs.FormParam;
import java.security.*;
import java.nio.charset.StandardCharsets;
import de.ls5.wt2.DBUser;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;
import de.ls5.wt2.conf.auth.jwt.*;
import com.nimbusds.jose.*;
import de.ls5.wt2.DBUser_;
import javax.persistence.NoResultException;
@Path("/auth/user")
@Transactional
public class UserREST {

    @PersistenceContext
    private EntityManager entityManager;

    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response loginUser(@FormParam("username") String username, @FormParam("password") String password) {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBUser> query = builder.createQuery(DBUser.class);
        final Root<DBUser> from = query.from(DBUser.class);
        query.select(from);
        //query all users from DB
        final List<DBUser> result = this.entityManager.createQuery(query).getResultList();
        DBUser[] users = new DBUser[result.size()];
        users = result.toArray(users);
        for(int i=0; i<users.length; i++) {
          if(users[i].getUsername().equals(username)) {
            try {
            //matching user found - initialize MD5 hashing (md5 does not use salts)
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] newPw = md.digest();

            //compare hashes
            StringBuilder sb = new StringBuilder();
            for(int x=0; x<newPw.length; x++) {
              sb.append(Integer.toString((newPw[x] & 0xff) + 0x100, 16).substring(1));
            }
            if(sb.toString().equals(users[i].getPassword().toString())) {
              String JWTToken = "";
              JWTUtil util = new JWTUtil();
              JWTLoginData utilData = new JWTLoginData();
              utilData.setUsername(users[i].getUsername().toString());
              utilData.setPassword(sb.toString());
              try {
                JWTToken = util.createJWToken(utilData);
                return Response.ok(JWTToken).build();
              }catch(JOSEException x) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
              }
            }
            }catch(NoSuchAlgorithmException e) {
              return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
            }
          }
        }    
        return Response.status(Response.Status.UNAUTHORIZED).build(); 
    }

    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response registerUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("firstname") String firstname,
      @FormParam("lastname") String lastname, @FormParam("email") String email) {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBUser> query = builder.createQuery(DBUser.class);
        final Root<DBUser> from = query.from(DBUser.class);
        query.select(from);
        query.where(builder.equal(from.get( DBUser_.username ), username ));
        try {
          DBUser foundUser = this.entityManager.createQuery(query).setMaxResults(1).getSingleResult();
          return Response.ok("username-already-taken").build();
        } catch (NoResultException e) {
          // Continue
        }
        final DBUser user = new DBUser();
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          md.update(password.getBytes());
          byte[] hashedPassword = md.digest();
          StringBuilder sb = new StringBuilder();
          for(int i=0; i<hashedPassword.length; i++) {
            sb.append(Integer.toString((hashedPassword[i] & 0xff) + 0x100, 16).substring(1));
          }
          user.setPassword(sb.toString());
          this.entityManager.persist(user);   
          return Response.ok(user).build();     
        }catch(NoSuchAlgorithmException e) {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
    }
}
