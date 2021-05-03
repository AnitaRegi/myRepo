package com.oracle.surveys.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.oracle.surveys.SurveysApplication;
import com.oracle.surveys.constants.ErrorCodes;
import com.oracle.surveys.constants.ErrorMessages;
import com.oracle.surveys.exception.ValidationException;
import com.oracle.surveys.model.entity.Survey;
import com.oracle.surveys.model.request.SurveyRequest;
import com.oracle.surveys.model.request.SurveyUpdateRequest;
import com.oracle.surveys.model.response.SurveyVersionResponse;
import com.oracle.surveys.service.SurveyService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SurveysApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SurveyControllerTests {

	@Mock
	private SurveyService surveyService;

	@Autowired
	SurveyController surveyController;

	@Before
	public void init() {
		surveyController = new SurveyController();
		MockitoAnnotations.openMocks(this);
		surveyService = Mockito.mock(surveyService.getClass());
		ReflectionTestUtils.setField(surveyController, "surveyService", surveyService);

	}

	@Test
	public void testAACreateSurveySuccess() throws Exception {

		SurveyRequest surveyRequest = new SurveyRequest();
		surveyRequest.setUserId(1L);
		surveyRequest.setName("Test Survey");
		surveyRequest.setDescription("Test Survey");
		surveyRequest.setStartDate(LocalDate.now());
		surveyRequest.setEndDate(LocalDate.now());

		Survey survey = new Survey();
		survey.setId(1L);
		survey.setCreatedBy(null);
		survey.setCreatedOn(null);
		survey.setUpdatedBy(null);
		survey.setUpdatedOn(null);
		survey.setVersions(null);
		Mockito.when(surveyService.create(surveyRequest)).thenReturn(survey);
		ResponseEntity<Survey> survey1 = surveyController.createSurvey(surveyRequest);

		Assert.assertNotNull(survey1.getBody());
		Assert.assertEquals(201, survey1.getStatusCode().value());

	}

	@Test
	public void testBACreateSurveyVersionSuccess() throws Exception {

		SurveyRequest surveyRequest = new SurveyRequest();
		surveyRequest.setUserId(1L);
		surveyRequest.setName("Test Survey");
		surveyRequest.setDescription("Test Survey");
		surveyRequest.setStartDate(LocalDate.now());
		surveyRequest.setEndDate(LocalDate.now());

		Survey survey = new Survey();
		survey.setId(1L);
		survey.setCreatedBy(null);
		survey.setCreatedOn(null);
		survey.setUpdatedBy(null);
		survey.setUpdatedOn(null);
		survey.setVersions(null);
		Mockito.when(surveyService.updateVersion(1L, surveyRequest)).thenReturn(survey);
		ResponseEntity<Survey> survey1 = surveyController.updateSurveyVersion(1L, surveyRequest);

		Assert.assertEquals(202, survey1.getStatusCode().value());

	}

	@Test
	public void testBBInvalidSurveyId() throws Exception {

		SurveyRequest surveyRequest = new SurveyRequest();
		surveyRequest.setUserId(1L);
		surveyRequest.setName("Test Survey");
		surveyRequest.setDescription("Test Survey");
		surveyRequest.setStartDate(LocalDate.now());
		surveyRequest.setEndDate(LocalDate.now());

		Survey survey = new Survey();
		survey.setId(1L);
		survey.setCreatedBy(null);
		survey.setCreatedOn(null);
		survey.setUpdatedBy(null);
		survey.setUpdatedOn(null);
		survey.setVersions(null);
		Mockito.when(surveyService.updateVersion(null, surveyRequest)).thenReturn(survey);
		try {
			surveyController.updateSurveyVersion(null, surveyRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("surveyId", e.getField());

		}

	}

	@Test
	public void testCAPatchSuccess() throws Exception {

		SurveyUpdateRequest surveyRequest = new SurveyUpdateRequest();
		surveyRequest.setUserId(1L);
		surveyRequest.setName("Test Survey");
		surveyRequest.setDescription("Test Survey");
		surveyRequest.setStartDate(LocalDate.now());
		surveyRequest.setEndDate(LocalDate.now());
		surveyRequest.setStatus("inactive");

		Survey survey = new Survey();
		survey.setId(1L);
		survey.setCreatedBy(null);
		survey.setCreatedOn(null);
		survey.setUpdatedBy(null);
		survey.setUpdatedOn(null);
		survey.setVersions(null);
		Mockito.when(surveyService.patchUpdate(surveyRequest, 1L, 1L)).thenReturn(survey);
		ResponseEntity<Survey> survey1 = surveyController.patchUpdateSurvey(1L, 1L, surveyRequest);

		Assert.assertEquals(202, survey1.getStatusCode().value());

	}

	@Test
	public void testCBPatchInvalidSurveyId() throws Exception {

		SurveyUpdateRequest surveyRequest = new SurveyUpdateRequest();
		surveyRequest.setUserId(1L);
		surveyRequest.setName("Test Survey");
		surveyRequest.setDescription("Test Survey");
		surveyRequest.setStartDate(LocalDate.now());
		surveyRequest.setEndDate(LocalDate.now());
		surveyRequest.setStatus("inactive");

		try {
			surveyController.patchUpdateSurvey(null, 1L, surveyRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("surveyId", e.getField());

		}

	}

	@Test
	public void testCCPatchInvalidVersion() throws Exception {

		SurveyUpdateRequest surveyRequest = new SurveyUpdateRequest();
		surveyRequest.setUserId(1L);
		surveyRequest.setName("Test Survey");
		surveyRequest.setDescription("Test Survey");
		surveyRequest.setStartDate(LocalDate.now());
		surveyRequest.setEndDate(LocalDate.now());
		surveyRequest.setStatus("inactive");

		try {
			surveyController.patchUpdateSurvey(1L, null, surveyRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("version", e.getField());

		}

	}
	
	
	@Test
	public void testDAfindSurveyByIdSuccess() throws Exception {

		Mockito.when(surveyService.findById(1L, 1L)).thenReturn(new SurveyVersionResponse());
		ResponseEntity<SurveyVersionResponse> survey1 = surveyController.findSurveyById(1L, 1L);

		Assert.assertEquals(200, survey1.getStatusCode().value());

	}
	
	@Test
	public void testDBFindByIdInvalidSurveyId() throws Exception {

		try {
			surveyController.findSurveyById(null, 1L);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("surveyId", e.getField());

		}

	}

	@Test
	public void testDCFindByIdInvalidVersion() throws Exception {

		try {
			surveyController.findSurveyById(1L, null);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("version", e.getField());

		}

	}
	
	@Test
	public void testDAfindByUserSuccess() throws Exception {

		Mockito.when(surveyService.findAllByUser(1L)).thenReturn(new ArrayList<>());
		ResponseEntity<List<Survey>> survey1 = surveyController.findAllSurveysByUser(1L);

		Assert.assertEquals(200, survey1.getStatusCode().value());

	}
}
