package com.example.IShopStore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Value("${file.upload-dir}")
        private String uploadDir;

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {

                registry.addViewController("/")
                                .setViewName("forward:/index.html");

                registry.addViewController("/admin/**")
                                .setViewName("forward:/index.html");

                registry.addViewController("/users/**")
                                .setViewName("forward:/index.html");

                registry.addViewController("/**")
                                .setViewName("forward:/index.html");
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                String finalUploadDir = this.uploadDir.endsWith("/") ? this.uploadDir : this.uploadDir + "/";

                registry.addResourceHandler("/upload-images/**")
                                .addResourceLocations("file:///" + finalUploadDir);

                registry.addResourceHandler("/**")
                                .addResourceLocations("classpath:/static/");
        }

}
