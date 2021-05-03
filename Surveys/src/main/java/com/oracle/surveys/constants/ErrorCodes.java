package com.oracle.surveys.constants;

import lombok.Getter;

@Getter
public enum ErrorCodes {

	REQUIRED_PARAM_MISSING("SRV001"),
	SURVEY_DOESNT_EXIST("SRV002"),
	SURVEY_VERSION_DOESNT_EXIST("SRV003"),
	SURVEYQN_DOESNT_EXIST("SRV004"),
	USR_RESPONSE_DOESNT_EXIST("SRV005");
	
	private String value;

	ErrorCodes(String value){
		this.value =value;
	}
}
