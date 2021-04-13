package com.oracle.surveys.constants;

import lombok.Getter;

@Getter
public enum ErrorMessages {

	REQUIRED_PARAM_MISSING("Required parameter missing."),
	SURVEY_DOESNT_EXIST("Survey doesn't exist!"),
	SURVEYQN_DOESNT_EXIST("SurveyQuestion doesn't exist!");
	private String value;

	ErrorMessages(String value){
		this.value =value;
	}
}
