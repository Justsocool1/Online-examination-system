package com.lins.config;

import com.lins.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    // 注入 token 拦截器
    @Resource
    private TokenInterceptor interceptor;

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        WebMvcConfigurer.super.addInterceptors(registry);
        // 添加自定义拦截器，并拦截对应 url"
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/user/login");
    }
}
