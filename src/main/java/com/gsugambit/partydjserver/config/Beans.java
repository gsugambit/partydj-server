package com.gsugambit.partydjserver.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Custom beans to be added into spring context
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
        filter.setIncludeHeaders(true);
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

}
