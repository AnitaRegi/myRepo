package com.oracle.surveys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.surveys.model.SurveyQuestionDto;
import com.oracle.surveys.model.entity.Question;
import com.oracle.surveys.model.entity.SurveyQuestion;
import com.oracle.surveys.service.SurveyQuestionService;
import com.oracle.surveys.validation.service.SurveyQuestionValidator;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController

@Slf4j
public class SurveyQuestionsController {

	@Autowired
	private SurveyQuestionService surveyQnService;

	/**
	 * Creates a survey question for surveyId and version.
	 * 
	 * @param surveyId        id of the survey for which question is being created.
	 * @param surveyQnRequest Contains fields to create the SurveyQuestion.
	 * @return surveyQuestion SurveyQuestion details.
	 */
	@ApiOperation(value = "Creates a survey question for surveyId and version.")
	@PostMapping("/surveys/{surveyId}/{version}/questions")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SurveyQuestion> createSurveyQuestion(@PathVariable Long surveyId, @PathVariable Long version,
			@RequestBody SurveyQuestionDto surveyQnRequest) {

		log.info("Initiating survey qn creation for surveyId {} version {}....", surveyId, version);

		SurveyQuestionValidator.validateSurveyId(surveyId);
		SurveyQuestionValidator.validateVersion(version);

		SurveyQuestion surveyQuestion = surveyQnService.create(surveyId, version, surveyQnRequest);

		log.info("SurveyQuestion created Successfully....");

		return new ResponseEntity<SurveyQuestion>(surveyQuestion, HttpStatus.CREATED);

	}

	/**
	 * Updates the fields of a surveyQuestion.
	 * 
	 * @param surveyId        Id of the survey for which question is being updated.
	 * @param questionId      Id of the question that is being updated.
	 * @param surveyQnRequest Contains fields with values to update the survey.
	 * @return surveyQuestion Updated survey details.
	 */
	@ApiOperation(value = "Updates the fields of a surveyQuestion.")
	@PutMapping("/surveys/{surveyId}/{version}/questions/{questionId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SurveyQuestion> updateSurveyQuestion(@PathVariable Long surveyId, @PathVariable Long version,
			@PathVariable Long questionId, @RequestBody SurveyQuestionDto surveyQnRequest) {

		log.info("Updating surveyQuestion {}....", questionId);

		SurveyQuestionValidator.validateSurveyId(surveyId);
		SurveyQuestionValidator.validateVersion(version);
		SurveyQuestionValidator.validateQuestionId(questionId);

		SurveyQuestion surveyQuestion = surveyQnService.update(surveyQnRequest, surveyId, version, questionId);

		log.info("surveyQuestion updated Successfully....");

		return new ResponseEntity<SurveyQuestion>(surveyQuestion, HttpStatus.ACCEPTED);

	}

	/**
	 * Selectively updates the fields of a surveyQuestion.
	 * 
	 * @param surveyId      Id of the survey to be updated.
	 * @param surveyRequest Contains fields with values to update the survey.
	 * @return Survey Updated survey details created along with surveyId.
	 */
	@ApiOperation(value = "Selectively updates the fields of a surveyQuestion.")
	@PatchMapping("/surveys/{surveyId}/{version}/questions/{questionId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SurveyQuestion> patchUpdateSurvey(@PathVariable Long surveyId, @PathVariable Long version,
			@PathVariable Long questionId, @RequestBody SurveyQuestionDto surveyQnRequest) {
		log.info("Updating surveyQuestion {}....", surveyId);

		SurveyQuestionValidator.validateSurveyId(surveyId);
		SurveyQuestionValidator.validateVersion(version);
		SurveyQuestionValidator.validateQuestionId(questionId);

		SurveyQuestion surveyQuestion = surveyQnService.patchUpdate(surveyQnRequest, surveyId, version, questionId);

		log.info("surveyQuestion updated Successfully....");

		return new ResponseEntity<SurveyQuestion>(surveyQuestion, HttpStatus.ACCEPTED);

	}

	/**
	 * Gets the surveyQuestion of the specified questionId.
	 * 
	 * @param surveyId
	 * @param version
	 * @param questionId
	 */
	@ApiOperation(value = "Gets the surveyQuestion of the specified questionId.")
	@GetMapping("/surveys/{surveyId}/{version}/questions/{questionId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Question> findByQuestionId(@PathVariable Long surveyId, @PathVariable Long version,
			@PathVariable Long questionId) {

		log.info("Retrieving question {} from survey {}- {}....", questionId, surveyId, version);

		SurveyQuestionValidator.validateSurveyId(surveyId);
		SurveyQuestionValidator.validateVersion(version);
		SurveyQuestionValidator.validateQuestionId(questionId);

		Question question = surveyQnService.findById(surveyId, version, questionId);

		log.info("Question fetched Successfully....");

		return new ResponseEntity<Question>(question, HttpStatus.OK);

	}


	/**
	 * Returns all questions of the given survey and version.
	 * 
	 * @param surveyId
	 * 
	 * @param version
	 * 
	 * @return
	 */
	@ApiOperation(value = "Returns all questions of the given survey and version.")

	@GetMapping("/surveys/{surveyId}/{version}/questions")
	public ResponseEntity<SurveyQuestion> findQuestionsById(@PathVariable Long surveyId, @PathVariable Long version) {

		log.info("Fetching all questions of survey {}- {}....", surveyId, version);

		SurveyQuestionValidator.validateSurveyId(surveyId);
		SurveyQuestionValidator.validateVersion(version);

		SurveyQuestion surveys = surveyQnService.findAllQuestionBySurvey(surveyId, version);

		log.info("Successfully fetched all survey questions....");

		return new ResponseEntity<SurveyQuestion>(surveys, HttpStatus.OK);

	}



}
