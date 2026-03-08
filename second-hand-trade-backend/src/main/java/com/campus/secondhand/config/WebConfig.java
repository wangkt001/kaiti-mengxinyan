package com.campus.secondhand.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射静态资源到项目根目录下的upload-images目录
        String uploadPath = "D:/work/兼职/毕业设计/2026-03-06/kaiti-mengxinyan/upload-images/";
        registry.addResourceHandler("/api/upload-images/**")
                .addResourceLocations("file:" + uploadPath);
    }
}
