package com.trafny.classroomlibrary.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private StudentAuthInterceptor studentAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(studentAuthInterceptor)
                .addPathPatterns("/students/**")
                .excludePathPatterns(
                        "/students/login",
                        "/students/sign-out",
                        "/css/**",
                        "/images/**",
                        "/js/**"
                );
    }
}
