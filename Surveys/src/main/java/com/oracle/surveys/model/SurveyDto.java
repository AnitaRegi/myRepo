package com.oracle.surveys.model;

import java.sql.Date;

import com.oracle.surveys.model.entity.Survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDto {
	private String name;
	private Long userId;
	private String description;
	private String version;
	private Long startDate;
	private Long endDate;
	private String status;
	
	
	public Survey toEntity() {
		Survey surveyEntity = new Survey();
		surveyEntity.setName(this.name);
		surveyEntity.setDescription(this.description);
		surveyEntity.setCreatedBy(this.userId);
		surveyEntity.setVersion(this.version);
		surveyEntity.setStartDate(new Date(this.startDate.longValue()));
		surveyEntity.setEndDate(new Date(this.startDate.longValue()));
		surveyEntity.setStatus(this.status);
		return surveyEntity;
	}
}
