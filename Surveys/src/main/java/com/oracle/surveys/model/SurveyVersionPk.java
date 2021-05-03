package com.oracle.surveys.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SurveyVersionPk implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long surveyId;
	private Long version;

}
