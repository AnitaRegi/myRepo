package com.oracle.surveys.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userResponseId;
	private Long userId;
	private Long surveyId;
	private Long version;
	private Boolean completed;
	
	@OneToMany(targetEntity = UserAnswer.class, cascade = CascadeType.ALL, orphanRemoval = true,
			fetch = FetchType.EAGER, mappedBy = "userResponse")
	private List<UserAnswer> answers;
	
	public UserResponse() {
		if(CollectionUtils.isEmpty(this.answers)) {
			this.setAnswers(new ArrayList<>());
		}
	}
}
