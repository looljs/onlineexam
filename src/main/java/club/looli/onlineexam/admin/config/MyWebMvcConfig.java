package club.looli.onlineexam.admin.config;

import club.looli.onlineexam.admin.interceptor.LoginInterceptor;
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/**"
        ).excludePathPatterns(
                "/admin/system/login",
                "/admin/system/get_cpacha",
                "/static/**"
        );
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/role/roleList").setViewName("role/role_list");
//
//        registry.addViewController("/user/userList").setViewName("user/user_list");
        registry.addViewController("/admin/system/index").setViewName("index");
        registry.addViewController("/admin/system/welcome").setViewName("welcome");
    }
}