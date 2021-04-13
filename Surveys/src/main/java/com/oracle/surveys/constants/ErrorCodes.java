package com.oracle.surveys.constants;

import lombok.Getter;

@Getter
public enum ErrorCodes {

	REQUIRED_PARAM_MISSING("SRV001"),
	SURVEY_DOESNT_EXIST("SRV002"),
	SURVEYQN_DOESNT_EXIST("SRV003");
	
	private String value;

	ErrorCodes(String value){
		this.value =value;
	}
}
