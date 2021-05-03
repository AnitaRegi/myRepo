package com.oracle.surveys.model.request;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oracle.surveys.model.entity.Survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class SurveyRequest {
	protected String name;
	protected Long userId;
	protected String description;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(style = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss")
	protected LocalDate startDate;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(style = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss")
	protected LocalDate endDate;

	
	
	public Survey toEntity() {
		
		log.debug("SurveyRequest.toEntity() entered...");

		Survey surveyEntity = new Survey();
		surveyEntity.setCreatedBy(this.userId);
		surveyEntity.setUpdatedBy(this.userId);
		surveyEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		log.debug("SurveyRequest.toEntity() exited...");

		return surveyEntity;
	}
}
