package com.gsugambit.partydjserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.gsugambit.partydjserver.http.OperationIdIntercepter;
import com.gsugambit.partydjserver.security.JwtFilter;
import com.gsugambit.partydjserver.security.PartyDJAuthenticationEntryPoint;
import com.gsugambit.partydjserver.security.TokenHelper;
import com.gsugambit.partydjserver.service.PartyDjUserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_DATA_ENDPOINTS = { "/authenticate/*", "/api/v1/user" };

	private static final String[] AUTH_WHITELIST = { "/v2/**", "/swagger-resources", "/swagger-resources/**",
			"/swagger-ui.html", "/webjars/**", "/configuration/ui", "/configuration/security", "/actuator/*", "/h2",
			"/h2/", "/h2/**" };

	private static final String[] DATA_ENDPOINTS = { "/api/v1/**", "/api/v1/*", "/graphql" };

	private final PartyDJAuthenticationEntryPoint entryPoint;
	private final TokenHelper tokenHelper;
	private final PasswordEncoder passwordEncoder;
	private final boolean webDebug;
	private final PartyDjUserDetailsService userDetailsService;

	@Autowired
	public WebSecurityConfig(final PartyDJAuthenticationEntryPoint entryPoint, final TokenHelper tokenHelper,
			final PasswordEncoder passwordEncoder, @Value("${partydj.web.debug:true}") final boolean webDebug,
			final PartyDjUserDetailsService userDetailsService) {
		this.entryPoint = entryPoint;
		this.tokenHelper = tokenHelper;
		this.passwordEncoder = passwordEncoder;
		this.webDebug = webDebug;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.authenticationEntryPoint(entryPoint).and().authorizeRequests()
				.antMatchers(new String[0]/* DATA_ENDPOINTS */).authenticated().anyRequest().permitAll().and()
				.addFilterBefore(new JwtFilter(tokenHelper, userDetailsService), BasicAuthenticationFilter.class)
				.addFilterBefore(new OperationIdIntercepter(), JwtFilter.class);

		http.csrf().disable();
	}

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(webDebug);
		web.ignoring().antMatchers(AUTH_DATA_ENDPOINTS).and().ignoring().antMatchers(AUTH_WHITELIST).and().ignoring()
				.antMatchers(DATA_ENDPOINTS);
	}
}
