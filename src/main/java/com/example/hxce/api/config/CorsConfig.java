package com.example.hxce.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有接口路径
                .allowedOriginPatterns("*") // 允许所有跨域源（生产环境建议指定具体域名）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许所有请求方法
                .allowCredentials(true) // 是否允许携带Cookie等凭证
                .maxAge(3600); // 预检请求的有效期（秒），避免频繁发送预检请求
    }
}
