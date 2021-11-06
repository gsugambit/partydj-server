package com.gsugambit.partydjserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

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

}
