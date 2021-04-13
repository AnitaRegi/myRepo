package com.oracle.surveys.auth;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.oracle.surveys.exception.SurveyException;
import com.oracle.surveys.model.UserResponseDto;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<UserResponseDto> userResponse = restTemplate.exchange("http://localhost:8080/users/{username}",
				HttpMethod.GET, null, UserResponseDto.class, username);
		UserResponseDto userResponseDTO = userResponse.getBody();
		if (null == userResponseDTO) {
			throw new SurveyException("username NOT Found", username, null);
		}

		Set<String> roleNames = userResponseDTO.getRoles().stream().map(role -> "ROLE_"+role.getName())
				.collect(Collectors.toSet());
		List<GrantedAuthority> grantedAuthorities = roleNames.stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new User( username, userResponseDTO.getPassword(), grantedAuthorities);
	}

}
