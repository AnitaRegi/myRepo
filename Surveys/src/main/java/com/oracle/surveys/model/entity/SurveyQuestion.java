package com.oracle.surveys.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

import com.oracle.surveys.model.SurveyPk;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Slf4j
public class SurveyQuestion {

	@EmbeddedId
	private SurveyPk id;

	@OneToMany(targetEntity = Question.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumns(value = { @JoinColumn(name = "survey_id"), @JoinColumn(name = "version") })
	private Set<Question> question;

	public SurveyQuestion(SurveyPk id) {
		if (CollectionUtils.isEmpty(this.question)) {
			this.question = new HashSet<>();
		}
		this.id = id;
	}

	public void addQuestions(Question question) {
		log.debug("Inside SurveyQuestion.addQuestions().Current no. of questions is {}...", this.question.size());
		this.question.add(question);
		log.debug("Question added to survey...Total no. of questions updated to {}", this.question.size());

	}

	public void removeQuestions(Long questionId) {
		log.debug("Inside SurveyQuestion.removeQuestions().Current no. of questions is {}...", this.question.size());
		
		if (!CollectionUtils.isEmpty(this.question)) {
			this.question.removeIf(qn-> qn.getQuestionId().equals(questionId));
		}
		log.debug("Question removed from survey...Total no. of questions updated to {}", this.question.size());

	}

}
