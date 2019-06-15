package de.ls5.wt2;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class StartupBean {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void startup() {

        final DBNews firstNewsItem = this.entityManager.find(DBNews.class, 1L);

        // only initialize once
        if (firstNewsItem == null) {
            final DBNews news = new DBNews();

            news.setHeadline("Startup");
            news.setContent("Startup Bean successfully executed");
            news.setPublishedOn(new Date());

            this.entityManager.persist(news);
        }
    }

    @PreDestroy
    public void shutdown() {
        // potential cleanup work
    }
}
