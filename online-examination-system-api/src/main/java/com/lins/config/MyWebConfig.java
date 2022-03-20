package com.lins.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        设置允许跨域的路径
        registry.addMapping("/**")
//                是否允许证书
                .allowCredentials(true)
//                设置允许跨域请求的域名
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("POST", "GET", "DELETE", "PUT", "OPTIONS")
                .allowedHeaders("*")
//                跨域允许时间
                .maxAge(3600);
    }

}
