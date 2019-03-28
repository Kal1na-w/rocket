package ua.od.atomspace.sarafan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/**")
                .allowedOrigins("http://127.0.0.1:8080")
                .allowedMethods("PUT", "DELETE","GET")
                .allowedHeaders("header1","header2","header3")
                .exposedHeaders("header1", "header2")
                .allowCredentials(true).maxAge(3600);
    }
}
