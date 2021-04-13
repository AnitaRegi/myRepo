package com.oracle.surveys.model;

import com.oracle.surveys.model.entity.AnswersOffered;
import com.oracle.surveys.model.entity.Question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class SurveyQuestionDto {

	private String surveyVersion;
	private String qnName;
	private Integer order;
	private String qnType;
	private String optionOne;
	private String optionTwo;
	private String optionThree;
	private String optionFour;
	private String other;
	
	public Question toEntity() {

		log.debug("SurveyQuestionDto.toEntity() Entered...");

		Question surveyQn = new Question();
		surveyQn.setQnName(this.qnName);
		surveyQn.setQnType(this.qnType);
		
		AnswersOffered answer = new AnswersOffered();
		answer.setOptionOne(this.optionOne);
		answer.setOptionTwo(this.optionTwo);
		answer.setOptionThree(this.optionThree);
		answer.setOptionFour(this.optionFour);
		answer.setOther(this.other);
		
		surveyQn.setAnswer(answer);
		log.debug("SurveyQuestionDto.toEntity() Exited...");

		return surveyQn;
	}
}
