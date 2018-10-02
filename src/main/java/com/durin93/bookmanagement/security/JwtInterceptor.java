package com.durin93.bookmanagement.security;

import com.durin93.bookmanagement.exception.JwtAuthorizationException;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    private static final String HEADER_AUTH = "Authorization";

    private JwtManager jwtManager;

    public JwtInterceptor(JwtManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final Optional<String> token = Optional.ofNullable(request.getHeader(HEADER_AUTH));

        if(!token.isPresent()){
            throw new JwtAuthorizationException(ErrorManager.NOT_EXIST_TOKEN);
        }
        token.ifPresent(jwt-> jwtManager.parse(token.get()));
        logger.debug("token {}", token.get());

        return true;
    }

}

