package com.gsugambit.partydjserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

	@Bean	
	public WebMvcConfigurerBean webMvcConfigurerBean() {
		return new WebMvcConfigurerBean();
	}
}
