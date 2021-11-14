package com.gsugambit.partydjserver.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Custom beans to be added into spring context
 * 
 * @author gsugambit
 *
 */
@Configuration
public class Beans {

	@Bean
	public WebMvcConfigurerBean webMvcConfigurerBean() {
		return new WebMvcConfigurerBean();
	}

	@Bean
	public CommonsRequestLoggingFilter logFilter() {
		final CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(10000);
		filter.setIncludeClientInfo(true);
		filter.setIncludeQueryString(true);
		filter.setAfterMessagePrefix("REQUEST DATA : ");
		return filter;
	}

	@Bean
	public NetHttpTransport netHttpTransport() {
		return new NetHttpTransport();
	}

	@Bean
	public JacksonFactory jacksonFactory() {
		return new JacksonFactory();
	}

	@Bean
	public HttpRequestInitializer httpRequestInitializer() {
		return new HttpRequestInitializer() {
			public void initialize(HttpRequest request) throws IOException {
			}
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public Module datatypeHibernateModule() {
		return new Hibernate5Module();
	}
}
