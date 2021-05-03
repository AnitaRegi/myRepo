package com.oracle.users.auth;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String HEADER_STRING; //Authorization

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX; //Bearer

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    	System.out.println("hi18");
    	log.debug("Inside JwtAuthenticationFilter.doFilterInternal() entered..." );
    	String header = req.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
        	authToken = header.replace(TOKEN_PREFIX,"");
        	System.out.println(authToken);
            try {
            	
            	
                username = jwtTokenUtil.getUsernameFromToken(authToken);
           
            
            } catch (IllegalArgumentException e) {
            	log.error("An error occurred while fetching Username from Token", e);
            } catch (ExpiredJwtException e) {
            	log.warn("The token has expired", e);
            } catch(SignatureException e){
            	log.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
            logger.warn("Couldn't find bearer string, header will be ignored.");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	System.out.println("hi19");

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
            	
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                log.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
        
    	log.debug("Inside JwtAuthenticationFilter.doFilterInternal() exited..." );

    }
}
