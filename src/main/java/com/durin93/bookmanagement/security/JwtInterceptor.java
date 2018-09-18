package com.durin93.bookmanagement.security;

import com.durin93.bookmanagement.exception.JwtAuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    private static final String HEADER_AUTH = "Authorization";

    private JwtManager jwtManager;

    public JwtInterceptor(JwtManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String token = request.getHeader(HEADER_AUTH);
        log.debug("token {}", token);
        if (token != null && jwtManager.isUsable(token)) {
            return true;
        } else {
            throw new JwtAuthorizationException();
        }

    }

}
