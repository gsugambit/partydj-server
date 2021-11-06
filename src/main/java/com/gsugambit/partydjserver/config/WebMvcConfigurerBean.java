package com.gsugambit.partydjserver.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfigurerBean implements WebMvcConfigurer{
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
