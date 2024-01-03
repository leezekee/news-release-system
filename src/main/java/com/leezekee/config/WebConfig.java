package com.leezekee.config;

import com.leezekee.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns(
                        "/admin/login",
                        "/chiefEditor/login",
                        "/journalist/login",
                        "/news/list",
                        "/news/detail/{id}",
                        "/news/search",
                        "/image/detail/{imageId}",
                        "/image/list/{newsId}"
                );
    }
}
