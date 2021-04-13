package com.oracle.surveys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	@PostMapping("/surveys/{surveyId}/questions")
	public ResponseEntity<SurveyQuestion> createSurveyQuestion(@PathVariable Long surveyId,
			@RequestBody SurveyQuestionDto surveyQnRequest) {

		log.info("Initiating survey qn creation for surveyId {} version {}....", surveyId,
				surveyQnRequest.getSurveyVersion());
		SurveyQuestion surveyQuestion = surveyQnService.create(surveyId, surveyQnRequest);
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
	@PutMapping("/surveys/{surveyId}/questions/{questionId}")
	public ResponseEntity<SurveyQuestion> updateSurveyQuestion(@PathVariable Long surveyId,
			@PathVariable Long questionId, @RequestBody SurveyQuestionDto surveyQnRequest) {

		log.info("Updating surveyQuestion {}....", questionId);
		SurveyQuestion surveyQuestion = surveyQnService.update(surveyQnRequest, surveyId, questionId);
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
	@PatchMapping("/surveys/{surveyId}/questions/{questionId}")
	public ResponseEntity<SurveyQuestion> patchUpdateSurvey(@PathVariable Long surveyId, @PathVariable Long questionId,
			@RequestBody SurveyQuestionDto surveyQnRequest) {
		log.info("Updating surveyQuestion {}....", surveyId);
		SurveyQuestion surveyQuestion = surveyQnService.patchUpdate(surveyQnRequest, surveyId, questionId);
		log.info("surveyQuestion updated Successfully....");
		return new ResponseEntity<SurveyQuestion>(surveyQuestion, HttpStatus.ACCEPTED);

	}

	/**
	 * Returns all surveysQuestions.
	 * 
	 * @return List of SurveyQuestion.
	 */
	@GetMapping("/surveys/questions")
	public ResponseEntity<List<SurveyQuestion>> findAllQuestions() {

		log.info("Fetching all questions....");
		List<SurveyQuestion> surveys = surveyQnService.findAll();
		log.info("Sucessfully fetched all questions....");

		return new ResponseEntity<List<SurveyQuestion>>(surveys, HttpStatus.OK);

	}

	/**
	 * Returns all questions of the given survey and version.
	 * 
	 * @param surveyId
	 * @param version
	 * @return
	 */
	@GetMapping("/surveys/{surveyId}/{version}/questions")
	public ResponseEntity<SurveyQuestion> findQuestionsById(@PathVariable Long surveyId, @PathVariable String version) {

		log.info("Fetching all questions of survey {}- {}....", surveyId, version);
		SurveyQuestion surveys = surveyQnService.findById(surveyId, version);
		log.info("Successfully fetched all survey questions....");

		return new ResponseEntity<SurveyQuestion>(surveys, HttpStatus.OK);

	}

	/**
	 * Gets the surveyQuestion of the specified questionId.
	 * 
	 * @param surveyId
	 * @param version
	 * @param questionId
	 */
	@GetMapping("/surveys/{surveyId}/{version}/questions/{questionId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Question> findByQuestionId(@PathVariable Long surveyId, @PathVariable String version,
			@PathVariable Long questionId) {

		log.info("Retrieving question {} from survey {}- {}....", questionId, surveyId, version);

		Question question = surveyQnService.findByQuestionId(surveyId, version, questionId);
		log.info("Question fetched Successfully....");
		return new ResponseEntity<Question>(question, HttpStatus.OK);

	}

	/**
	 * Deletes the surveyQuestion of the specified id.
	 * 
	 * @param surveyId
	 * @param version
	 * @param questionId
	 */
	@DeleteMapping("/surveys/{surveyId}/{version}/questions/{questionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSurvey(@PathVariable Long surveyId, @PathVariable String version, @PathVariable Long questionId) {

		log.info("Deleting question from survey {}- {}....", surveyId, version);

		surveyQnService.delete(surveyId, version, questionId);

		log.info("Survey deleted Successfully....");

	}

}
