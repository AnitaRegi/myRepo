package com.oracle.surveys.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserResponse {

	@Id
	private Long userResponseId;
	private String userName;
	private Long questionId;
	@Column(nullable = false)
	private String answer;
}
