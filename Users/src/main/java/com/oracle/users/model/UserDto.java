package com.oracle.users.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oracle.users.model.entity.User;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class UserDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String name;
	private Boolean isAdmin;

	@JsonIgnore
	public User getUserFromDto() {

		log.debug("Inside UserDto.getUserFromDto() entered...");
		User user = new User(username, password, email, phone, name);
		log.debug("Inside UserDto.getUserFromDto() exited...");
		return user;
	}
}
