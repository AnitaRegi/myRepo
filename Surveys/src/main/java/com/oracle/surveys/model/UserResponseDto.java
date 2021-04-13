package com.oracle.surveys.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String email;
	private String password;
	private String phone;
	private String name;
	private Set<Role> roles = new HashSet<>();
}	
