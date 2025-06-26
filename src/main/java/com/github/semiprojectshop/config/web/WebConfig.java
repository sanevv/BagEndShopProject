package com.github.semiprojectshop.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.upload.directory}")
    private String uploadPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 외부 업로드 폴더 매핑
        registry.addResourceHandler("/uploads/**")//예 /uploads/image.jpg 즉 /uploads/로 시작하는 URL 요청을 처리
                .addResourceLocations("file:///"+uploadPath) // file:///은 시스템의 루트 경로를 뜻함 그이후 C:/upload/ 등등을 작성
                .setCachePeriod(3600);

        // 기존 static 리소스 매핑
        registry.addResourceHandler("/**")// 그외에 모든 URL 요청을 처리
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);
    }
}
