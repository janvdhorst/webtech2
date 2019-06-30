package de.ls5.wt2.rest;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import de.ls5.wt2.DBNews;
import de.ls5.wt2.DBNews_;
import de.ls5.wt2.conf.auth.jwt.*;
import javax.ws.rs.FormParam;

@Path("/news")
@Transactional
public class NewsCRUD {

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Path("/newest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readNewestNews() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBNews> query = builder.createQuery(DBNews.class);

        final Root<DBNews> from = query.from(DBNews.class);

        final Order order = builder.desc(from.get(DBNews_.publishedOn));

        query.select(from).orderBy(order);

        return Response.ok(this.entityManager.createQuery(query).setMaxResults(1).getSingleResult()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(final DBNews param) {

        //Verify JWT
        JWTUtil util = new JWTUtil();
        if(util.validateToken(param.getHeadline())) {
          String sender = util.getSender(param.getHeadline());
          final DBNews news = new DBNews();
          news.setHeadline(sender);
          news.setContent(param.getContent());
          news.setPublishedOn(new Date());
          this.entityManager.persist(news);
          return Response.ok(news).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build(); 
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DBNews> readAllAsJSON() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBNews> query = builder.createQuery(DBNews.class);
        final Root<DBNews> from = query.from(DBNews.class);
        query.select(from);
        return this.entityManager.createQuery(query).getResultList();
    }

    @Path("/{id}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public DBNews readAsJSON(@PathParam("id") final long id) {
        return this.entityManager.find(DBNews.class, id);
    }

    @Path("/delete")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response delete(@FormParam("id") int pId, @FormParam("jwt") String jwt) {
      //verify jwt
      JWTUtil util = new JWTUtil();
      System.out.println("------------------------------------------------");
      System.out.println("Request to delete NEWS " + pId);
      if(util.validateToken(jwt.toString())) {
          String sender = util.getSender(jwt.toString());
          System.out.println("JWT successfully authenticated as " + sender);
          CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
          CriteriaQuery<DBNews> query = builder.createQuery(DBNews.class);
          Root<DBNews> from = query.from(DBNews.class);
          query.select(from);
          query.where(builder.equal(from.get( DBNews_.id ), pId ));
          DBNews news = this.entityManager.createQuery(query).setMaxResults(1).getSingleResult();
          if( news == null ) {
            return Response.status(Response.Status.NOT_FOUND).build();
          }
          if(!news.getHeadline().equals(sender.toString())) {
            System.out.println("Unauthorized");
            System.out.println(news.getHeadline());
            System.out.println(sender);
            return Response.status(Response.Status.UNAUTHORIZED).build();
          }
          this.entityManager.remove(news);
          return Response.ok(pId).build();
      }
      else {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
    }
}
