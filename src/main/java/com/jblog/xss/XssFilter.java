package com.jblog.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class XssFilter implements Filter {

    private String excludeUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludeUrl = filterConfig.getInitParameter("excludeUrl");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(((HttpServletRequest)request).getServletPath());
        if (((HttpServletRequest)request).getServletPath().matches(excludeUrl)) {
            chain.doFilter(request, response);
        } else {
            XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
                    (HttpServletRequest) request);
            chain.doFilter(xssRequest, response);
        }

    }

    @Override
    public void destroy() {

    }
}
