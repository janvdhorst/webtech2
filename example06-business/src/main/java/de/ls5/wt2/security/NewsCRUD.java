package de.ls5.wt2.security;

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.ls5.wt2.DBNews;
import de.ls5.wt2.DBNews_;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

@Path("/security/news")
@Transactional
public class NewsCRUD {

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Path("newest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readNewestNews() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBNews> query = builder.createQuery(DBNews.class);

        final Root<DBNews> from = query.from(DBNews.class);

        final Order order = builder.desc(from.get(DBNews_.publishedOn));

        query.select(from).orderBy(order);

        return Response.ok(this.entityManager.createQuery(query).setMaxResults(1).getSingleResult()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readAllAsJSON() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBNews> query = builder.createQuery(DBNews.class);

        final Root<DBNews> from = query.from(DBNews.class);

        query.select(from);

        final List result = this.entityManager.createQuery(query).getResultList();
        return Response.ok(result).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUnsafe(final DBNews param) {

        final DBNews news = new DBNews();

        news.setHeadline(param.getHeadline());
        // news.setContent(param.getContent());
        news.setPublishedOn(new Date());

        this.entityManager.persist(news);
        this.entityManager.flush();

        int upd = this.entityManager.createNativeQuery(
                "UPDATE DBNews SET content = '" + param.getContent() + "' WHERE id = " + news.getId() + ';')
                                    .executeUpdate();

        System.err.println(upd);

        return Response.ok(this.entityManager.find(DBNews.class, news.getId())).build();
    }
}
