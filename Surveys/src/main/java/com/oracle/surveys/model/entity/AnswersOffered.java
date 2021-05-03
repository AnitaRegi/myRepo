package com.oracle.surveys.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsExclude;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="answers_offered")
public class AnswersOffered implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(length =5)
	@EqualsExclude
	private Long answerId;
	
	@JsonInclude(Include. NON_NULL)
	private String optionOne;
	
	@JsonInclude(Include. NON_NULL)
	private String optionTwo;
	
	@JsonInclude(Include. NON_NULL)
	private String optionThree;
	
	@JsonInclude(Include. NON_NULL)
	private String optionFour;
	
	@JsonInclude(Include. NON_NULL)
	private String other;
	

}
