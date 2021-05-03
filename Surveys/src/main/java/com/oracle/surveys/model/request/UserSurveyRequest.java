package com.oracle.surveys.model.request;

import java.util.List;

import com.oracle.surveys.model.entity.UserAnswer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSurveyRequest {

	private Boolean completed;
	private List<UserAnswer> userAnswer;
}
