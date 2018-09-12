package com.durin93.bookmanagement;

import com.durin93.bookmanagement.support.LoginUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


	@Bean
	public LoginUserHandlerMethodArgumentResolver loginUserArgumentResolver() {
		return new LoginUserHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(loginUserArgumentResolver());
	}

}
