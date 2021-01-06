package cn.bupt.edu.spring_hmk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class Configuration implements WebMvcConfigurer {
    @Value("${user.file.path}")
    private String filePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/resources/templates/",
                "file:/"+filePath,
                "classpath:/resources/static/"
        );
    }
}
