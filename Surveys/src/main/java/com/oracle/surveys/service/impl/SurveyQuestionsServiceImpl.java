package com.oracle.surveys.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.oracle.surveys.constants.ErrorCodes;
import com.oracle.surveys.constants.ErrorMessages;
import com.oracle.surveys.exception.SurveyException;
import com.oracle.surveys.model.SurveyPk;
import com.oracle.surveys.model.SurveyQuestionDto;
import com.oracle.surveys.model.entity.Question;
import com.oracle.surveys.model.entity.Survey;
import com.oracle.surveys.model.entity.SurveyQuestion;
import com.oracle.surveys.repo.QuestionRepository;
import com.oracle.surveys.repo.SurveyQuestionRepository;
import com.oracle.surveys.repo.SurveyRepository;
import com.oracle.surveys.service.SurveyQuestionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SurveyQuestionsServiceImpl implements SurveyQuestionService {

	@Autowired
	SurveyRepository surveyRepo;
	@Autowired
	QuestionRepository qnRepo;
	@Autowired
	SurveyQuestionRepository sqnRepo;

	@Override
	public SurveyQuestion create(Long surveyId, SurveyQuestionDto request) {

		log.debug("SurveyQuestionsServiceImpl.create() Entered...");

		SurveyPk id = new SurveyPk(surveyId, request.getSurveyVersion());
		Optional<Survey> surveyOpt = surveyRepo.findBySurveyIdAndVersion(surveyId, request.getSurveyVersion());

		surveyOpt.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(),
				ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), HttpStatus.BAD_REQUEST));

		Optional<SurveyQuestion> surveyQnOpt = sqnRepo.findById(id);
		SurveyQuestion sqn = surveyQnOpt.orElse(new SurveyQuestion(id));
		Question question = request.toEntity();
		sqn.addQuestions(question);

		log.debug("SurveyQuestionsServiceImpl.create() Exited...");

		return sqnRepo.save(sqn);
	}

	@Override
	public SurveyQuestion update(SurveyQuestionDto request, Long surveyId, Long questionId) {

		log.debug("SurveyQuestionsServiceImpl.update() Entered...");
		

		SurveyQuestion sqn = findById(surveyId, request.getSurveyVersion());

		Question qn = sqn.getQuestion().stream().filter(obj -> obj.getQuestionId().equals(questionId)).findFirst()
				.get();

		qn.setQnName(request.getQnName());
		qn.setQnType(request.getQnType());
		qn.getAnswer().setOptionOne(request.getOptionOne());
		qn.getAnswer().setOptionTwo(request.getOptionTwo());
		qn.getAnswer().setOptionThree(request.getOptionThree());
		qn.getAnswer().setOptionFour(request.getOptionFour());
		qn.getAnswer().setOther(request.getOther());
		sqn.addQuestions(qn);
		log.debug("SurveyQuestionsServiceImpl.update() Exited...");

		return sqnRepo.save(sqn);
	}

	@Override
	public SurveyQuestion patchUpdate(SurveyQuestionDto request, Long surveyId, Long questionId) {

		log.debug("SurveyQuestionsServiceImpl.patchUpdate() Entered...");
		SurveyQuestion sqn = findById(surveyId, request.getSurveyVersion());

		Question surveyQn = sqn.getQuestion().stream().filter(obj -> obj.getQuestionId().equals(questionId)).findFirst()
				.get();

		if (StringUtils.isNotBlank(request.getQnName())) {
			surveyQn.setQnName(request.getQnName());
		}
		if (StringUtils.isNotBlank(request.getQnType())) {
			surveyQn.setQnType(request.getQnType());
		}
		/*if (ObjectUtils.isNotEmpty(request.getOptionOne())) {
			surveyQn.getAnswer().setOptionOne(request.getOptionOne());
		}
		if (ObjectUtils.isNotEmpty(request.getOptionTwo())) {
			surveyQn.getAnswer().setOptionTwo(request.getOptionTwo());
		}*/
		if (StringUtils.isNotBlank(request.getOptionThree())) {
			surveyQn.getAnswer().setOptionThree(request.getOptionThree());
		}
		if (StringUtils.isNotBlank(request.getOptionFour())) {
			surveyQn.getAnswer().setOptionFour(request.getOptionFour());
		}
		if (StringUtils.isNotBlank(request.getOther())) {
			surveyQn.getAnswer().setOther(request.getOther());
		}

		log.debug("SurveyQuestionsServiceImpl.patchUpdate() Exited...");
		sqn.addQuestions(surveyQn);

		return sqnRepo.save(sqn);
	}

	@Override
	public SurveyQuestion findById(Long surveyId, String version) {

		log.debug("SurveyQuestionsServiceImpl.findById() Entered...");
		
		SurveyPk id = new SurveyPk(surveyId, version);
		Optional<SurveyQuestion> surveyOpt = sqnRepo.findById(id);

		SurveyQuestion surveyQuestion = surveyOpt.map(obj -> {
			return surveyOpt.get();
		}).orElseThrow(() -> new SurveyException(ErrorCodes.SURVEYQN_DOESNT_EXIST.getValue(),
				ErrorMessages.SURVEYQN_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		log.debug("SurveyQuestionsServiceImpl.findById() Exited...");

		return surveyQuestion;
	}

	@Override
	public List<SurveyQuestion> findAll() {

		log.debug("Inside SurveyQuestionsServiceImpl.findAll()...");
		return (List<SurveyQuestion>) sqnRepo.findAll();

	}
	
	@Override
	public Question findByQuestionId(Long surveyId, String version, Long questionId) {
		log.debug("SurveyQuestionsServiceImpl.findByQuestionId() Entered...");

		SurveyQuestion sqn = findById(surveyId, version);
		Optional<Question> questionOpt = sqn.getQuestion().stream().filter(qn->qn.getQuestionId().equals(questionId)).findFirst();
		log.debug("SurveyQuestionsServiceImpl.findByQuestionId() Entered...");

		return questionOpt.map(obj -> {
			return questionOpt.get();
		}).orElseThrow(() -> new SurveyException(ErrorCodes.SURVEYQN_DOESNT_EXIST.getValue(),
				ErrorMessages.SURVEYQN_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));
	}

	@Override
	public void delete(Long surveyId, String version, Long questionId ) {

		log.debug("Inside SurveyQuestionsServiceImpl.delete()...");
		SurveyQuestion sqn = findById(surveyId, version);
		sqn.removeQuestions(questionId);
		log.debug("Successfully deleted surveyquestion...");
	}

}
