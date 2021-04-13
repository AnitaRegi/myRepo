package com.oracle.surveys.model.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Survey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
	@GenericGenerator(
		      name = "sequence-generator",
		      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
		      parameters = {
		        @Parameter(name = "sequence_name", value = "survey_sequence"),
		        @Parameter(name = "initial_value", value = "10000"),
		        @Parameter(name = "increment_size", value = "1")
		        }
		    )
	
	private Long surveyId;
	private String version;

	@Column(nullable = false)
	private String name;
	private Long createdBy;
	private String description;
	private Date startDate;
	private Date endDate;
	private String status;

}
