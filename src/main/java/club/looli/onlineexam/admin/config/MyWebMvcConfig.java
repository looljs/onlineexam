package club.looli.onlineexam.admin.config;

import club.looli.onlineexam.admin.interceptor.LoginInterceptor;
import club.looli.onlineexam.home.interceptor.HomeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private HomeInterceptor homeInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/admin/**"
        ).excludePathPatterns(
                "/admin/system/login",
                "/admin/system/get_cpacha",
                "/static/**"
        );
        registry.addInterceptor(homeInterceptor).addPathPatterns("/home/**").excludePathPatterns(
                "/static/**",
                "/home/login","/home/register"
        );
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/system/index").setViewName("index");
        registry.addViewController("/admin/system/welcome").setViewName("welcome");
    }
}
