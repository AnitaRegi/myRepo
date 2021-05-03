package com.oracle.surveys.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.oracle.surveys.model.entity.Question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQuestionAnswer {

	private Question qn;
	
	@JsonInclude(Include.NON_NULL)
	private String answerOne;
	
	@JsonInclude(Include.NON_NULL)
	private String answerTwo;
	
	@JsonInclude(Include.NON_NULL)
	private String answerThree;
	
	@JsonInclude(Include.NON_NULL)
	private String answerFour;
	
	@JsonInclude(Include.NON_NULL)
	private String other;

}
