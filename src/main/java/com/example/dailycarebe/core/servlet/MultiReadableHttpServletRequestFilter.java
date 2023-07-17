package com.example.dailycarebe.core.servlet;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MultiReadableHttpServletRequestFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        MultiReadableHttpServletRequest multiReadRequest = new MultiReadableHttpServletRequest((HttpServletRequest) req);
        RequestContextHolder.currentRequestAttributes().setAttribute(MultiReadableHttpServletRequest.class.getSimpleName(), multiReadRequest, RequestAttributes.SCOPE_REQUEST);
        chain.doFilter(multiReadRequest, res);
        RequestContextHolder.currentRequestAttributes().removeAttribute(MultiReadableHttpServletRequest.class.getSimpleName(), RequestAttributes.SCOPE_REQUEST);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
