package cn.bupt.edu.spring_hmk.config;

import cn.bupt.edu.spring_hmk.filter.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
@Configuration
public class Config implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] urls = {"/postblog", "/postCommit","/likeBlog","/likeCommit"};
        registry.addInterceptor(new Interceptor()).addPathPatterns(Arrays.asList(urls));
    }
}
