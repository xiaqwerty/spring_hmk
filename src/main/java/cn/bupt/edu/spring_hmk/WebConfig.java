package cn.bupt.edu.spring_hmk;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        //registry.addInterceptor(new Interceptor1()).addPathPatterns("/myAddressBook","/addPerson","index");
        registry.addInterceptor(new Interceptor1()).addPathPatterns("/**").excludePathPatterns("/myFrontPage","/css/*","/js/*","/login","/myFrontPage.html");
    }
    /*@Override   //使用/resource/访问静态资源的方法
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/resource/**").addResourceLocations("classpath:/static/css/","classpath:/static/js/","classpath:/static/images/").setCachePeriod(31556926);
    }*/

}
