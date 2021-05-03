package com.oracle.surveys.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

import com.oracle.surveys.model.SurveyVersionPk;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Slf4j
@IdClass(SurveyVersionPk.class)
@ToString
public class SurveyQuestion {

	@Id
	private Long surveyId;

	@Id
	private Long version;

	@OneToMany(targetEntity = Question.class, cascade = CascadeType.ALL, orphanRemoval = true,
			 fetch = FetchType.EAGER, mappedBy = "surveyQuestion")
	private Set<Question> question;

	public SurveyQuestion(Long surveyId, Long version) {
		if (CollectionUtils.isEmpty(this.question)) {
			this.setQuestion(new HashSet<>());
		}
		this.surveyId = surveyId;
		this.version = version;
	}

	public void addQuestions(Question question) {
		log.debug("Inside SurveyQuestion.addQuestions() started...");
		log.debug("Current no. of questions is {}...", this.question.size());
		this.question.add(question);
		log.debug("Question added to survey...Total no. of questions updated to {}", this.question.size());

	}

	public void removeQuestions(Long questionId) {
		log.debug("Inside SurveyQuestion.removeQuestions().Current no. of questions is {}...", this.question.size());

		if (!CollectionUtils.isEmpty(this.question)) {
			this.question.removeIf(qn -> qn.getQuestionId().equals(questionId));
		}
		log.debug("Question removed from survey...Total no. of questions updated to {}", this.question.size());

	}

}
