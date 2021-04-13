package com.oracle.surveys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
@Embeddable
public class SurveyPk implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long surveyId;
	@Column(length = 5)
	private String version;

}
