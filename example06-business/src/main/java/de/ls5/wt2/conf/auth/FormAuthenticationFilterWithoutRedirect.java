package de.ls5.wt2.conf.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class FormAuthenticationFilterWithoutRedirect extends FormAuthenticationFilter {

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) {
        // do not redirect
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e,
                                     ServletRequest request,
                                     ServletResponse response) {
        WebUtils.toHttp(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
