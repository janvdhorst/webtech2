package de.ls5.wt2.conf.auth.permission;

import org.apache.shiro.authz.Permission;

public class ReadNewsItemPermission implements Permission {

    @Override
    public boolean implies(Permission p) {

        if (p instanceof ViewFirstFiveNewsItemsPermission) {
            final ViewFirstFiveNewsItemsPermission fnip = (ViewFirstFiveNewsItemsPermission) p;
            return fnip.check();
        }

        return false;
    }
}
