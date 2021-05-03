package com.oracle.surveys.web;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.surveys.model.entity.Survey;
import com.oracle.surveys.model.request.SurveyRequest;
import com.oracle.surveys.model.request.SurveyUpdateRequest;
import com.oracle.surveys.model.response.SurveyVersionResponse;
import com.oracle.surveys.service.SurveyService;
import com.oracle.surveys.validation.service.SurveyQuestionValidator;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/surveys")
@Slf4j
public class SurveyController {

	@Autowired
	private SurveyService surveyService;

	/**
	 * Creates a survey for the given request.
	 * 
	 * @param surveyRequest Contains fields to create the survey.
	 * @return Survey Survey details created along with surveyId.
	 */
	
	@ApiOperation(value = "Creates a survey from the given request.")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Survey> createSurvey(@RequestBody SurveyRequest surveyRequest) {
		log.info("Initiating survey creation for {}....", surveyRequest.getName());

		Survey survey = surveyService.create(surveyRequest);

		log.info("Survey created Successfully....");

		return new ResponseEntity<Survey>(survey, HttpStatus.CREATED);

	}

	/**
	 * Creates a new survey Version.
	 * 
	 * @param surveyId Id of the survey to be updated.
	 * @return Survey Updated survey details created along with surveyId.
	 */
	
	@ApiOperation(value = "Creates a new survey Version.")
	@PutMapping("/{surveyId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Survey> updateSurveyVersion(@PathVariable Long surveyId,
			@RequestBody SurveyRequest surveyRequest) {

		log.info("Updating survey {}....", surveyId);

		SurveyQuestionValidator.validateSurveyId(surveyId);

		Survey survey = surveyService.updateVersion(surveyId, surveyRequest);

		log.info("Survey updated Successfully....");

		return new ResponseEntity<Survey>(survey, HttpStatus.ACCEPTED);

	}

	/**
	 * Selectively updates the fields of a survey.
	 * 
	 * @param surveyId      Id of the survey to be updated.
	 * @param surveyRequest Contains fields with values to update the survey.
	 * @return Survey Updated survey details created along with surveyId.
	 */
	
	@ApiOperation(value = "Selectively updates the fields of a survey.")
	@PatchMapping("/{surveyId}/version/{version}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Survey> patchUpdateSurvey(@PathVariable Long surveyId, @PathVariable Long version,
			@RequestBody SurveyUpdateRequest surveyRequest) {

		log.info("Updating survey {}....", surveyId);

		SurveyQuestionValidator.validateSurveyId(surveyId);
		SurveyQuestionValidator.validateVersion(version);

		Survey survey = surveyService.patchUpdate(surveyRequest, surveyId, version);

		log.info("Survey updated Successfully....");

		return new ResponseEntity<Survey>(survey, HttpStatus.ACCEPTED);

	}

	/**
	 * Returns the survey details of the specified survey version.
	 * 
	 * @return Survey
	 */
	@ApiOperation(value = "Fetches the survey details of the specified survey version.")
	@GetMapping("/{surveyId}/version/{version}")
	public ResponseEntity<SurveyVersionResponse> findSurveyById(@PathVariable Long surveyId,
			@PathVariable Long version) {

		log.info("Fetching survey details of survey: {}-{}.", surveyId, version);

		SurveyQuestionValidator.validateSurveyId(surveyId);
		SurveyQuestionValidator.validateVersion(version);

		SurveyVersionResponse surveys = surveyService.findById(surveyId, version);
		log.info("Successfully fetched survey details of survey: {}-{}.", surveyId, version);

		return new ResponseEntity<SurveyVersionResponse>(surveys, HttpStatus.OK);

	}

	/**
	 * Returns all surveys created by an admin user.
	 * 
	 * @return List of Surveys
	 */
	@ApiOperation(value = "Returns all surveys created by an admin user.")
	@GetMapping("/admin/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Survey>>findAllSurveysByUser(@PathVariable Long userId) 
	{
		log.info("Fetching survey details of user: {}.", userId);

		List<Survey> surveys = surveyService.findAllByUser(userId); 
		
		log.info("Successfully Fetched {} surveys of user {}.", surveys.size(), userId);

		return new ResponseEntity<List<Survey>>(surveys, HttpStatus.OK);
	

    }
}



