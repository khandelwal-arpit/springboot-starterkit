package com.starterkit.springboot.brs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Arpit Khandelwal.
 */
@Configuration
public class PageConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/dashboard").setViewName("dashboard");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/agency").setViewName("agency");
        registry.addViewController("/bus").setViewName("bus");
        registry.addViewController("/trip").setViewName("trip");
        registry.addViewController("/logout").setViewName("logout");
    }

}
