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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.surveys.model.entity.SurveyQuestion;
import com.oracle.surveys.model.entity.SurveyVersion;
import com.oracle.surveys.model.entity.UserResponse;
import com.oracle.surveys.model.request.UserSurveyPatchRequest;
import com.oracle.surveys.model.request.UserSurveyRequest;
import com.oracle.surveys.service.UserSurveyService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("")
@Slf4j
public class UserSurveyResponseController {

	@Autowired
	private UserSurveyService userSurveyService;

	/**
	 * Creates a survey response with the given request.
	 * 
	 * @param userId
	 * @param version
	 * @param surveyId
	 * @param request
	 * @return UserResponse UserResponse details created along with UserResponseId.
	 */

	@ApiOperation(value = "Creates a survey response with the given request")
	@PostMapping("/users/{userId}/survey/{surveyId}/{version}")
	public ResponseEntity<UserResponse> createSurvey(@PathVariable Long userId, @PathVariable Long version,
			@PathVariable Long surveyId, @RequestBody UserSurveyRequest request) {

		log.info("Initiating survey response creation for....");

		UserResponse survey = userSurveyService.create(userId, surveyId, version, request);

		log.info("Survey Response created Successfully....");

		return new ResponseEntity<UserResponse>(survey, HttpStatus.CREATED);

	}

	/**
	 * Marks a userResponse as completed.
	 * 
	 * @param surveyId      Id of the survey to be updated.
	 * @param UserSurveyPatchRequest Contains fields with values to update the userResponse.
	 * @return UserResponse Updated user response details.
	 */

	@ApiOperation(value = "Marks a userResponse as completed")
	@PatchMapping("/users/")
	public ResponseEntity<UserResponse> patchUpdateSurvey(UserSurveyPatchRequest request) {

		log.info("Updating userResponse {}....", request.getUserResponseId());

		UserResponse userResponse = userSurveyService.patchUpdate(request);

		log.info("userResponse updated Successfully....");

		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.ACCEPTED);

	}

	/**
	 * Returns all surveys that are pending to be completed by a user.
	 * 
	 * @return List of SurveyQuestion
	 */
	@ApiOperation(value = "Returns all surveys that are pending to be completed by a user.")
	@GetMapping("/users/{userId}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<SurveyQuestion>> findPendingSurveys(@PathVariable Long userId) {

		log.info("Fetching active surveys for Users ...");

		List<SurveyQuestion> surveys = userSurveyService.findPendingSurveys(userId);

		log.info("Retrieved {} active surveys...", surveys.size());
		return new ResponseEntity<List<SurveyQuestion>>(surveys, HttpStatus.OK);

	}

	/**
	 * Retrieves all active surveys for USER.
	 * 
	 * @return List of SurveyVersion
	 */
	@ApiOperation(value = "Retrieves all active surveys for USER")
	@GetMapping("/users/surveys/active")
	public ResponseEntity<List<SurveyVersion>> findActiveSurveys() {

		log.info("Fetching active surveys for Users ...");

		List<SurveyVersion> surveys = userSurveyService.findActiveSurveys();

		log.info("Retrieved {} active surveys...", surveys.size());
		return new ResponseEntity<List<SurveyVersion>>(surveys, HttpStatus.OK);

	}

	/**
	 * Retrieves a survey response by user.
	 * 
	 * @return UserResponse
	 */
	@ApiOperation(value = "Retrieves a survey response by user.")
	@GetMapping("/users/{userId}/survey/{surveyId}/{version}")
	public ResponseEntity<UserResponse> findById(@PathVariable Long  userId, @PathVariable Long  surveyId,Long version) {

		log.info("Fetching survey response for User{} ...", userId);

		UserResponse userResponse = userSurveyService.findById(userId, surveyId,version);

		log.info("Retrieved user response...");
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);

	}
	
	
	/**
	 * Retrieves user response attempted by all users.
	 * 
	 * @return List of UserResponses
	 */
	@ApiOperation(value = "Retrieves user response attempted by all users.(For Admin)")
	@GetMapping("/survey/{surveyId}/{version}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserResponse>> findById( @PathVariable Long  surveyId,Long version) {

		log.info("Fetching survey response for survey {}---{}...", surveyId, version);

		List<UserResponse> userResponses = userSurveyService.findAllBySurvey( surveyId,version);

		log.info("Retrieved user response...");
		return new ResponseEntity<List<UserResponse>>(userResponses, HttpStatus.OK);

	}

}
