package com.example.IShopStore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/")
                .setViewName("forward:/index.html");

        registry.addViewController("/admin/**")
                .setViewName("forward:/index.html");

        registry.addViewController("/users/**")
                .setViewName("forward:/index.html");

        // Fallback catch-all cho các đường dẫn khác (nếu cần)
        registry.addViewController("/**")
                .setViewName("forward:/index.html");
    }
}