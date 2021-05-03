package com.oracle.surveys.model.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyVersionResponse {

	private Long surveyId;
	private Long version;
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private String status;
}
