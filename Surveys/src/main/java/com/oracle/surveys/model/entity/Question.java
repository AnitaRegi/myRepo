package com.oracle.surveys.model.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints=@UniqueConstraint(
		columnNames={"qnName","qnType","survey_id","version"}))
public class Question implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qnsequence-generator")
	@GenericGenerator(name = "qnsequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "qn_sequence"),
			@Parameter(name = "initial_value", value = "1000"), @Parameter(name = "increment_size", value = "1") })
	private Long questionId;
	
	private String qnName;
	private String qnType;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "answerId")
	private AnswersOffered answer;
	
    @ManyToOne
    @JoinColumns(value = {@JoinColumn(name = "survey_id"), @JoinColumn(name = "version")})
    @JsonIgnore
    private SurveyQuestion surveyQuestion;
	
}
