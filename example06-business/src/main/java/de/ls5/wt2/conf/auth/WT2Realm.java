package de.ls5.wt2.conf.auth;

import java.util.Collection;
import java.util.Collections;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;

import de.ls5.wt2.conf.auth.permission.ReadNewsItemPermission;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

public class WT2Realm extends AuthorizingRealm implements Realm {

    final static String REALM = "WT2";

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        final Instance<DatabaseAuthenticator> authenticatorInstance = CDI.current().select(DatabaseAuthenticator.class);

        if (!authenticatorInstance.isResolvable()) {
            throw new AuthenticationException();
        }

        final DatabaseAuthenticator authenticator = authenticatorInstance.get();

        return authenticator.fetchAuthenticationInfo(token);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new AuthorizationInfo() {

            @Override
            public Collection<String> getRoles() {
                if ("admin".equals(principals.getPrimaryPrincipal())) {
                    return Collections.singleton("admin");
                }

                return Collections.emptyList();
            }

            @Override
            public Collection<String> getStringPermissions() {
                return Collections.emptyList();
            }

            @Override
            public Collection<Permission> getObjectPermissions() {
                return Collections.singleton(new ReadNewsItemPermission());
            }
        };
    }
}
