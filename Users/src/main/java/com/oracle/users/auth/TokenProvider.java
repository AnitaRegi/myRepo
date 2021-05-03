package com.oracle.users.auth;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider implements Serializable {


	private static final long serialVersionUID = -6371331658365634394L;

	@Value("${jwt.token.validity}")
    public long TOKEN_VALIDITY;

    @Value("${jwt.signing.key}")
    public String SIGNING_KEY;

    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY; //roles

    public String getUsernameFromToken(String token) {
    	
    	log.debug("Inside TokenProvider.getUsernameFromToken() ..." );
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
    	
    	log.debug("Inside TokenProvider.getExpirationDateFromToken() ..." );
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    	
    	log.debug("Inside TokenProvider.getClaimFromToken() entered..." );
        final Claims claims = getAllClaimsFromToken(token);
    	log.debug("Inside TokenProvider.getClaimFromToken() exited..." );

        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
    	log.debug("Inside TokenProvider.getAllClaimsFromToken() ..." );

        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
    	log.debug("Inside TokenProvider.isTokenExpired() entered..." );

        final Date expiration = getExpirationDateFromToken(token);
    	log.debug("Inside TokenProvider.isTokenExpired() exited..." );
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
    	log.debug("Inside TokenProvider.generateToken() entered..." );
        System.out.println(" hi15 Authentication : "+ authentication.toString());

         String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
         System.out.println("hi16");

         log.debug("Inside TokenProvider.generateToken() exited..." );
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
    	
    	log.debug("Inside TokenProvider.validateToken() entered..." );

        final String username = getUsernameFromToken(token);
    	
        log.debug("Inside TokenProvider.validateToken() exited..." );
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final UserDetails userDetails) {

    	log.debug("Inside TokenProvider.getAuthenticationToken() entered..." );

        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    	log.debug("Inside TokenProvider.getAuthenticationToken() exited..." );

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
