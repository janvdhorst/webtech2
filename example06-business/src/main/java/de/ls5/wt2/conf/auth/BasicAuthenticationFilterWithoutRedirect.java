package de.ls5.wt2.conf.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class BasicAuthenticationFilterWithoutRedirect extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        /** set no {@link AUTHENTICATE_HEADER}-header, so the browser does nothing **/
        WebUtils.toHttp(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
