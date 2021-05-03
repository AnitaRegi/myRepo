package com.oracle.surveys.model.response;

import com.oracle.surveys.model.entity.SurveyQuestion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PendingSurveyResponse {

	private SurveyQuestion survey;
	private Long userResponseId;
}
