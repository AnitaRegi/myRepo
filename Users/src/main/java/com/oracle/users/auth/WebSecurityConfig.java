package com.oracle.users.auth;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource(name = "userService")
	private UserDetailsService userDetailsService;

	@Autowired
	private UnauthorizedEntryPoint unauthorizedEntryPoint;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("hi7");
		log.debug("Inside WebSecurityConfig.configure(AuthenticationManagerBuilder auth) ...");
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
		System.out.println("hi8");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("hi9");

		log.debug("Inside WebSecurityConfig.configure(HttpSecurity http) entered...");
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/users/**" ,"actuator/**","/swagger-ui.html", "/configuration/**", "/swagger-resources/**", "/v2/api-docs",
						"/webjars/**").permitAll()
				.anyRequest()
				.authenticated().and().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		System.out.println("hi10");

		log.debug("Inside WebSecurityConfig.configure(HttpSecurity http) exited...");

	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		System.out.println("hi1");

		log.debug("Inside WebSecurityConfig.encoder() ...");
		System.out.println("hi2");

		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		System.out.println("hi3");

		log.debug("Inside WebSecurityConfig.authenticationManagerBean() ...");
		System.out.println("hi4");

		return super.authenticationManagerBean();
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		System.out.println("hi5");
		log.debug("Inside WebSecurityConfig.authenticationTokenFilterBean() ...");
		System.out.println("hi6");
		return new JwtAuthenticationFilter();
	}

}