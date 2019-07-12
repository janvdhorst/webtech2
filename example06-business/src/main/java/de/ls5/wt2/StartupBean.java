package de.ls5.wt2;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.nimbusds.jose.*;
import java.security.*;
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
            final DBNews news1 = new DBNews();
            final DBNews news2 = new DBNews();
            final DBNews news3 = new DBNews();
            final DBNews news4 = new DBNews();
            final DBNews news5 = new DBNews();
            final DBNews news6 = new DBNews();

            news1.setHeadline("satchel.paige");
            news1.setContent("Work like you don't need the money. Love like you've never been hurt. Dance like nobody's watching.");
            news1.setPublishedOn(new Date());
            
            news2.setHeadline("barney.stinson");
            news2.setContent("Believe it or not, I was not always as awesome as I am today");
            news2.setPublishedOn(new Date());
            
            news3.setHeadline("alexander.milne");
            news3.setContent("People say nothing is impossible, but i do nothing everyday!");
            news3.setPublishedOn(new Date());
            
            news4.setHeadline("abraham.lincoln");
            news4.setContent("Better to remain silent and be thought a fool than to speak out and remove all doubt.");
            news4.setPublishedOn(new Date());            
            
            news5.setHeadline("peter.griffin");
            news5.setContent("I have an idea so smart that my head would explode if I even began to know what I was talking about.");
            news5.setPublishedOn(new Date());
            
            news6.setHeadline("peter.griffin");
            news6.setContent("Love is like a fart. if you have to force it, it's probably crap.");
            news6.setPublishedOn(new Date());

            this.entityManager.persist(news1);
            this.entityManager.persist(news2);
            this.entityManager.persist(news3);
            this.entityManager.persist(news4);
            this.entityManager.persist(news5);
            this.entityManager.persist(news6);
        }

        final DBUser user = new DBUser();
        user.setUsername("admin");
        user.setFirstname("Chuck");
        user.setLastname("Norris");
        user.setEmail("admin@dda.com");
        user.setAdmin(1);

        String password = "webtech2";

          try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          md.update(password.getBytes());
          byte[] hashedPassword = md.digest();
          StringBuilder sb = new StringBuilder();
          for(int i=0; i<hashedPassword.length; i++) {
            sb.append(Integer.toString((hashedPassword[i] & 0xff) + 0x100, 16).substring(1));
          }
        
        user.setPassword(sb.toString());
          }catch ( NoSuchAlgorithmException e) {

          }
        this.entityManager.persist(user);
    }

    @PreDestroy
    public void shutdown() {
        // potential cleanup work
    }
}
