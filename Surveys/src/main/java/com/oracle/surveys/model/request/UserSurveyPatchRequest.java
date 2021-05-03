package com.oracle.surveys.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSurveyPatchRequest {

	private Long userResponseId;
	private Boolean completed;
}
