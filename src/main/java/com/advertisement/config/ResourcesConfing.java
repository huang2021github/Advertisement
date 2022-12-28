package com.advertisement.config;

import com.advertisement.oauth.LoginUserHandlerMethodArgumentResolver;
import com.advertisement.oauth.UserAuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ResourcesConfing implements WebMvcConfigurer {

    @Autowired
    private UserAuthorizationInterceptor userAuthorizationInterceptor;

    @Autowired
    private LoginUserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthorizationInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userHandlerMethodArgumentResolver);
    }
}
