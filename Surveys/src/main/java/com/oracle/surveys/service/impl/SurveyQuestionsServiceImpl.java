package com.oracle.surveys.service.impl;

import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.oracle.surveys.constants.ErrorCodes;
import com.oracle.surveys.constants.ErrorMessages;
import com.oracle.surveys.exception.SurveyException;
import com.oracle.surveys.model.SurveyQuestionDto;
import com.oracle.surveys.model.SurveyVersionPk;
import com.oracle.surveys.model.entity.AnswersOffered;
import com.oracle.surveys.model.entity.Question;
import com.oracle.surveys.model.entity.SurveyQuestion;
import com.oracle.surveys.model.entity.SurveyVersion;
import com.oracle.surveys.repo.SurveyQuestionRepository;
import com.oracle.surveys.repo.SurveyVersionRepository;
import com.oracle.surveys.service.SurveyQuestionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SurveyQuestionsServiceImpl implements SurveyQuestionService {

	@Autowired
	SurveyQuestionRepository surveyQnRepo;

	@Autowired
	SurveyVersionRepository surveyVersionRepo;

	/**
	 * Adds questions to the specified survey version.
	 */
	@Override
	public SurveyQuestion create(Long surveyId, Long version, SurveyQuestionDto request) {

		log.debug("SurveyQuestionsServiceImpl.create() Entered...");

		Optional<SurveyVersion> surveyVersionOpt = surveyVersionRepo.findBySurveyIdAndVersion(surveyId, version);

		surveyVersionOpt.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEY_VERSION_DOESNT_EXIST.getValue(),
				ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		SurveyQuestion surveyQuestion = surveyQnRepo.findById(new SurveyVersionPk(surveyId, version))
				.orElse(new SurveyQuestion(surveyId, version));
		Question question = request.toEntity();

		Long order = surveyQuestion.getQuestion().stream().max(Comparator.comparing(Question::getQnOrder))
				.orElse(question).getQnOrder();

		question.setQnOrder(order == null ? 1 : order + 1);
		question.setSurveyQuestion(surveyQuestion);

		surveyQuestion.addQuestions(question);

		log.debug("SurveyQuestionsServiceImpl.create() Exited...");

		return surveyQnRepo.save(surveyQuestion);
	}

	@Override
	public SurveyQuestion update(SurveyQuestionDto request, Long surveyId, Long version, Long questionId) {

		log.debug("SurveyQuestionsServiceImpl.update() Entered...");

		Optional<SurveyQuestion> surveyQnOpt = surveyQnRepo.findById(new SurveyVersionPk(surveyId, version));

		SurveyQuestion sqn = surveyQnOpt
				.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEYQN_DOESNT_EXIST.getValue(),
						ErrorMessages.SURVEYQN_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		Question qn = sqn.getQuestion().stream().filter(obj -> obj.getQuestionId().equals(questionId)).findAny().get();

		qn.setQnName(request.getQnText());
		qn.setQnType(request.getQnType());
		AnswersOffered answer = null != qn.getAnswer() ? qn.getAnswer() : new AnswersOffered();
		answer.setOptionOne(request.getOptionOne());
		answer.setOptionTwo(request.getOptionTwo());
		answer.setOptionThree(request.getOptionThree());
		answer.setOptionFour(request.getOptionFour());
		answer.setOther(request.getOther());

		qn.setAnswer(answer);
		qn.setSurveyQuestion(sqn);
		sqn.addQuestions(qn);
		log.debug("SurveyQuestionsServiceImpl.update() Exited...");

		return surveyQnRepo.save(sqn);
	}

	@Override
	public Question findById(Long surveyId, Long version, Long questionId) {

		log.debug("SurveyQuestionsServiceImpl.findById() Entered...");

		Optional<SurveyVersion> surveyVersionOpt = surveyVersionRepo.findBySurveyIdAndVersion(surveyId, version);
		surveyVersionOpt.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEY_VERSION_DOESNT_EXIST.getValue(),
				ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		SurveyQuestion surveyQn = findAllQuestionBySurvey(surveyId, version);

		Question question = surveyQn.getQuestion().stream().filter(obj -> obj.getQuestionId().equals(questionId))
				.findAny().orElseThrow(() -> new SurveyException(ErrorCodes.SURVEYQN_DOESNT_EXIST.getValue(),
						ErrorMessages.SURVEYQN_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		log.debug("SurveyQuestionsServiceImpl.findById() Exited...");

		return question;
	}

	@Override
	public SurveyQuestion findAllQuestionBySurvey(Long surveyId, Long version) {

		log.debug("Inside SurveyQuestionsServiceImpl.findAllQuestionBySurvey()...");

		return surveyQnRepo.findById(new SurveyVersionPk(surveyId, version))
				.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEYQN_DOESNT_EXIST.getValue(),
						ErrorMessages.SURVEYQN_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

	}

	@Override
	public SurveyQuestion patchUpdate(SurveyQuestionDto request, Long surveyId, Long version, Long questionId) {
		log.debug("Inside SurveyQuestionsServiceImpl.patchUpdate() entered...");

		Optional<SurveyQuestion> surveyQnOpt = surveyQnRepo.findById(new SurveyVersionPk(surveyId, version));

		SurveyQuestion sqn = surveyQnOpt
				.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEYQN_DOESNT_EXIST.getValue(),
						ErrorMessages.SURVEYQN_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		Question qn = sqn.getQuestion().stream().filter(obj -> obj.getQuestionId().equals(questionId)).findAny().get();

		if (null != request.getQnType()) {
			qn.setQnType(request.getQnType());
		}
		if (null != request.getQnText()) {
			qn.setQnName(request.getQnText());
		}

		if (isAnyAnswerFieldPresent(request)) {
			if (null == qn.getAnswer()) {
				qn.setAnswer(new AnswersOffered());
			}
			if (null != request.getOptionOne()) {
				qn.getAnswer().setOptionOne(request.getOptionOne());
			}
			if (null != request.getOptionTwo()) {
				qn.getAnswer().setOptionTwo(request.getOptionTwo());
			}
			if (null != request.getOptionThree()) {
				qn.getAnswer().setOptionThree(request.getOptionThree());
			}
			if (null != request.getOptionFour()) {
				qn.getAnswer().setOptionFour(request.getOptionFour());
			}
			if (null != request.getOther()) {
				qn.getAnswer().setOther(request.getOther());
			}
		}
		log.debug("Inside SurveyQuestionsServiceImpl.patchUpdate() exited...");

		return surveyQnRepo.save(sqn);

	};

	private Boolean isAnyAnswerFieldPresent(SurveyQuestionDto request) {
		if (null != request.getOptionOne() || null != request.getOptionTwo() || null != request.getOptionThree()
				|| null != request.getOptionFour() || null != request.getOther()) {
			return true;
		}
		return false;
	}

}