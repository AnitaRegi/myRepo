package com.oracle.surveys.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserAnswer {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long userAnswerId;
	private Long questionId;
	//for multiple answers like check box
	
	@JsonInclude(Include. NON_NULL)
	private String answerOne;
	
	@JsonInclude(Include. NON_NULL)
	private String answerTwo;
	
	@JsonInclude(Include. NON_NULL)
	private String answerThree;
	
	@JsonInclude(Include. NON_NULL)
	private String answerFour;
	
	@JsonInclude(Include. NON_NULL)
	private String other;
	
	
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name = "user_response_id")})
	@JsonIgnore
	private UserResponse userResponse;
}
