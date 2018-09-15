package com.durin93.bookmanagement;

import com.durin93.bookmanagement.support.JwtInterceptor;
import com.durin93.bookmanagement.support.JwtManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String[] EXCLUDE_PATHS = {
            "/api/users/**"
    };

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor(jwtManager());
    }

    @Bean
    public JwtManager jwtManager(){
        return new JwtManager();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }

}
