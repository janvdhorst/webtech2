package de.ls5.wt2.conf.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.LogoutFilter;

public class LogoutFilterWithoutRedirect extends LogoutFilter {

    @Override
    protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) {
        // do not redirect
    }
}
