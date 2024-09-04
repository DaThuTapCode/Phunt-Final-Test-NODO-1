package com.trongphu.finalintern1.config.cross;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Trong Phu on 01/09/2024 17:07
 *
 * @author Trong Phu
 */
@Configuration
public class WebCrossConfig implements WebMvcConfigurer {
    @Value("${frontend.url}")
    private String vueServer;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(vueServer)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
