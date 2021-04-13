package com.oracle.surveys.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenFilter authFilter;

	@Autowired
	JwtAuthenticationEntryPoint jwtAuthEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/swagger-ui.html", "/configuration/**", "/swagger-resources/**", "/v2/api-docs",
						"/webjars/**")
				.permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(jwtAuthEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
		web.ignoring().mvcMatchers("/swagger-ui.htm", "/configuration/**", "/swagger-resources/**", "/v2/api-docs",
				"/webjars/**");
	}
}
