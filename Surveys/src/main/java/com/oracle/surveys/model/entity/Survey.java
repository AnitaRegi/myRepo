package com.oracle.surveys.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.util.CollectionUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
public class Survey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
	@GenericGenerator(name = "sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "survey_sequence"),
			@Parameter(name = "initial_value", value = "10000"), @Parameter(name = "increment_size", value = "1") })

	private Long id;
	private Long createdBy;
	private Timestamp createdOn;
	private Long updatedBy;
	private Timestamp updatedOn;

	@OneToMany(targetEntity = SurveyVersion.class, cascade = CascadeType.ALL, orphanRemoval = true,
			fetch = FetchType.EAGER, mappedBy = "survey")
	private Set<SurveyVersion> versions;

	public void addVersions(SurveyVersion version) {
		log.debug("Inside SurveyQuestion.addVersions().");
		if(CollectionUtils.isEmpty(versions)) {
			versions = new HashSet<>();
		}
		log.debug("Current no. of questions is {}...", this.versions.size());

		this.versions.add(version);
		log.debug("Question added to survey...Total no. of questions updated to {}", this.versions.size());

	}


}
