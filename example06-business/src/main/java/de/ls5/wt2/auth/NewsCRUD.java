package de.ls5.wt2.auth;

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
import de.ls5.wt2.conf.auth.permission.ViewFirstFiveNewsItemsPermission;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;

@Path("/auth/{a:session|basic|jwt}/news")
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

        final DBNews result = this.entityManager.createQuery(query).setMaxResults(1).getSingleResult();

        // Attribute based permission check using permissions
        final Subject subject = SecurityUtils.getSubject();
        final Permission firstFiveNewsItemsPermission = new ViewFirstFiveNewsItemsPermission(result);

        if (!subject.isPermitted(firstFiveNewsItemsPermission)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok(result).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readAllAsJSON() {
        final Subject subject = SecurityUtils.getSubject();
        if (subject == null || !subject.isAuthenticated()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

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
    // Shiro annotations only work with byte-code weaving enabled.
    // This currently requires Java < 11. See https://github.com/mojohaus/aspectj-maven-plugin/pull/45
    @RequiresAuthentication
    @RequiresRoles("admin")
    public Response create(final DBNews param) {

        final DBNews news = new DBNews();

        news.setHeadline(param.getHeadline());
        news.setContent(param.getContent());
        news.setPublishedOn(new Date());

        this.entityManager.persist(news);

        return Response.ok(news).build();
    }
}
