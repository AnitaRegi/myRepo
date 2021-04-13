package com.oracle.surveys.auth;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider implements Serializable {

	private static final long serialVersionUID = -6371331658365634394L;

	@Value("${jwt.token.validity}")
	private long TOKEN_VALIDITY;

	@Value("${jwt.signing.key}")
	private String SIGNING_KEY;

	@Value("${jwt.token.prefix}")
	private String BEARER_PREFIX;
	
	@Value("${jwt.header.string}")
	private String AUTHORIZATION_HEADER;
	
	private final CustomUserDetailsService userDetailsService;

	public JwtTokenProvider(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public String getUsernameFromToken(String token) {

		log.debug("Inside JwtTokenProvider.getUsernameFromToken() ...");
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {

		log.debug("Inside JwtTokenProvider.getExpirationDateFromToken() ...");
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

		log.debug("Inside JwtTokenProvider.getClaimFromToken() entered...");
		final Claims claims = getAllClaimsFromToken(token);
		log.debug("Inside JwtTokenProvider.getClaimFromToken() exited...");

		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		log.debug("Inside JwtTokenProvider.getAllClaimsFromToken() ...");

		return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		log.debug("Inside JwtTokenProvider.isTokenExpired() entered...");

		final Date expiration = getExpirationDateFromToken(token);
		log.debug("Inside JwtTokenProvider.isTokenExpired() exited...");
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token) {

		log.debug("Inside JwtTokenProvider.validateToken() isExpired = {}...",isTokenExpired(token));
		return ( !isTokenExpired(token));
	}

	UsernamePasswordAuthenticationToken getAuthenticationToken(final String token) {

		log.debug("Inside JwtTokenProvider.getAuthenticationToken() entered...");
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsernameFromToken(token));

		log.debug("Inside JwtTokenProvider.getAuthenticationToken() exited...");

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String resolveToken(HttpServletRequest req) {
		
		log.debug("Inside JwtTokenProvider.resolveToken() ...");

		String bearerToken = req.getHeader(AUTHORIZATION_HEADER);

		return (!Objects.isNull(bearerToken) && bearerToken.startsWith(BEARER_PREFIX))
				? bearerToken.substring(7, bearerToken.length())
				: null;
	}

}
