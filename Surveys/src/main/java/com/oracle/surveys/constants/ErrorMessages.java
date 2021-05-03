package com.oracle.surveys.constants;

import lombok.Getter;

@Getter
public enum ErrorMessages {

	REQUIRED_PARAM_MISSING("Required parameter missing."),
	SURVEY_DOESNT_EXIST("Survey doesn't exist!"),
	SURVEY_VERSION_DOESNT_EXIST("SurveyVersion doesn't exist for the survey!"),
	SURVEYQN_DOESNT_EXIST("SurveyQuestion doesn't exist for the survey version!"),
	USR_RESPONSE_DOESNT_EXIST("Invalid request.UserResponse doesn't exist for the given responseId.");

	private String value;

	ErrorMessages(String value){
		this.value =value;
	}
}
