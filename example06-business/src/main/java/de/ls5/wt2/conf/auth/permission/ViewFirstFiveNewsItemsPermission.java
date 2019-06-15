package de.ls5.wt2.conf.auth.permission;

import de.ls5.wt2.DBNews;
import org.apache.shiro.authz.Permission;

public class ViewFirstFiveNewsItemsPermission implements Permission {

    private final DBNews news;

    public ViewFirstFiveNewsItemsPermission(final DBNews news) {
        this.news = news;
    }

    @Override
    public boolean implies(Permission p) {
        return false;
    }

    public boolean check() {
        return this.news.getId() < 5;
    }
}
