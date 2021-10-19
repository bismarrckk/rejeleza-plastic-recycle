package com.production.v1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfiguration implements WebMvcConfigurer {
	public static String uploadDirectory= System.getProperty("user.home") + "/uploads";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**","/admin/**","/frontend/**").addResourceLocations("file:" + uploadDirectory+"/","classpath:/static/admin/",
        		"classpath:/static/frontend/");
    }
}
