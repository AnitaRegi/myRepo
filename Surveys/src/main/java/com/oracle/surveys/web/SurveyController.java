package com.oracle.surveys.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.surveys.model.SurveyDto;
import com.oracle.surveys.model.entity.Survey;
import com.oracle.surveys.service.SurveyService;

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
	@PostMapping
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Survey> createSurvey(@RequestBody SurveyDto surveyRequest) {
		log.info("Initiating survey creation for {}....", surveyRequest.getName() );
		Survey survey = surveyService.create(surveyRequest);
		log.info("Survey created Successfully...." );
		return new ResponseEntity<Survey>(survey, HttpStatus.CREATED);

	}

	/**
	 * Updates a survey.
	 * 
	 * @param surveyId      Id of the survey to be updated.
	 * @param surveyRequest Contains fields with values to update the survey.
	 * @return Survey Updated survey details created along with surveyId.
	 */
	@PutMapping("/{surveyId}")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Survey> updateSurvey(@PathVariable Long surveyId, @RequestBody SurveyDto surveyRequest) {
		log.info("Updating survey {}....", surveyId );
		Survey survey = surveyService.update(surveyRequest, surveyId);
		log.info("Survey updated Successfully...." );
		return new ResponseEntity<Survey>(survey, HttpStatus.ACCEPTED);

	}

	/**
	 * Selectively updates the fields of a survey.
	 * 
	 * @param surveyId      Id of the survey to be updated.
	 * @param surveyRequest Contains fields with values to update the survey.
	 * @return Survey Updated survey details created along with surveyId.
	 */
	@PatchMapping("/{surveyId}")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Survey> patchUpdateSurvey(@PathVariable Long surveyId, @RequestBody SurveyDto surveyRequest) {
		log.info("Updating survey {}....", surveyId );
		Survey survey = surveyService.patchUpdate(surveyRequest, surveyId);
		log.info("Survey updated Successfully...." );
		return new ResponseEntity<Survey>(survey, HttpStatus.ACCEPTED);

	}

	/**
	 * Deletes the survey details of the specified id.
	 * 
	 * @param surveyId Id of the survey to be retrieved.
	 */
	@DeleteMapping("/{surveyId}/version/{version}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
	public void deleteSurvey(@PathVariable Long surveyId, @PathVariable String version) {
		surveyService.findById(surveyId,version);
		log.info("Survey deleted Successfully...." );

	}

	/**
	 * Returns all surveys.
	 * 
	 * @return List of Surveys
	 */
	@GetMapping
	public ResponseEntity<List<Survey>> findAllSurveys() {

		List<Survey> surveys = surveyService.findAll();
		return new ResponseEntity<List<Survey>>(surveys, HttpStatus.OK);

	}
	
	/**
	 * Returns all surveys created by a user.
	 * 
	 * @return List of Surveys
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Survey>> findAllSurveysByUser(@PathVariable Long userId) {

		List<Survey> surveys = surveyService.findAllByUser(userId);
		return new ResponseEntity<List<Survey>>(surveys, HttpStatus.OK);

	}
	
	/**
	 * Returns the survey details of the specified surveyId.
	 * 
	 * @return Survey
	 */
	@GetMapping("/{surveyId}/version/{version}")
	public ResponseEntity<Survey> findSurveyById(@PathVariable Long surveyId, @PathVariable String version) {
		log.info("Fetching survey details of {}.", surveyId );
		Survey surveys = surveyService.findById(surveyId, version);
		return new ResponseEntity<Survey>(surveys, HttpStatus.OK);

	}

}
