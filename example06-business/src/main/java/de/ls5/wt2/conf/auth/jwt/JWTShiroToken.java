package de.ls5.wt2.conf.auth.jwt;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTShiroToken implements AuthenticationToken {

    private final String username;
    private final String token;

    public JWTShiroToken(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return getUsername();
    }

    @Override
    public Object getCredentials() {
        return getToken();
    }
}
