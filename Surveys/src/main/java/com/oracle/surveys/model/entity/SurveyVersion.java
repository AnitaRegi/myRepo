package com.oracle.surveys.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"version","survey_id"}))
public class SurveyVersion {

	
	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	private Long version;
	@Column(nullable = false)
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private String status;

	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name = "survey_id")})
	@JsonIgnore
	private Survey survey;

}
