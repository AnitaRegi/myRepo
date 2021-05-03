package com.oracle.surveys.web;

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
import com.oracle.surveys.model.SurveyQuestionDto;
import com.oracle.surveys.model.entity.Question;
import com.oracle.surveys.model.entity.SurveyQuestion;
import com.oracle.surveys.service.SurveyQuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SurveysApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SurveyQuestionControllerTests {

	@Mock
	private SurveyQuestionService surveyqnService;

	@Autowired
	SurveyQuestionsController surveyQnController;

	@Before
	public void init() {
		surveyQnController = new SurveyQuestionsController();
		MockitoAnnotations.openMocks(this);
		surveyqnService = Mockito.mock(surveyqnService.getClass());
		ReflectionTestUtils.setField(surveyQnController, "surveyQnService", surveyqnService);

	}

	@Test
	public void testAACreateSurveyQnSuccess() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		SurveyQuestion surveyQn = new SurveyQuestion();
		surveyQn.setSurveyId(1L);
		surveyQn.setVersion(1L);
		surveyQn.setQuestion(null);

		Mockito.when(surveyqnService.create(1L, 1L, surveyQnRequest)).thenReturn(surveyQn);
		ResponseEntity<SurveyQuestion> survey1 = surveyQnController.createSurveyQuestion(1L, 1L, surveyQnRequest);

		Assert.assertNotNull(survey1.getBody());
		Assert.assertEquals(201, survey1.getStatusCode().value());

	}

	@Test
	public void testABInvalidSurveyId() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.createSurveyQuestion(null, 1L, surveyQnRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("surveyId", e.getField());

		}

	}

	@Test
	public void testACInvalidVersion() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.createSurveyQuestion(1L, null, surveyQnRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("version", e.getField());

		}

	}

	@Test
	public void testBAUpdateSuccess() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		SurveyQuestion surveyQn = new SurveyQuestion();
		surveyQn.setSurveyId(1L);
		surveyQn.setVersion(1L);
		surveyQn.setQuestion(null);

		Mockito.when(surveyqnService.update(surveyQnRequest, 1L, 1L, 1L)).thenReturn(surveyQn);
		ResponseEntity<SurveyQuestion> survey1 = surveyQnController.updateSurveyQuestion(1L, 1L, 1L, surveyQnRequest);

		Assert.assertEquals(202, survey1.getStatusCode().value());

	}

	@Test
	public void testBBInvalidSurveyId() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.updateSurveyQuestion(null, 1L, 1L, surveyQnRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("surveyId", e.getField());

		}

	}

	@Test
	public void testBCInvalidVersion() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.updateSurveyQuestion(1L, null, 1L, surveyQnRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("version", e.getField());

		}

	}

	@Test
	public void testBDInvalidQuestionId() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.updateSurveyQuestion(1L, 1L, null, surveyQnRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("questionId", e.getField());

		}

	}

	@Test
	public void testCAPatchUpdateSuccess() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		SurveyQuestion surveyQn = new SurveyQuestion();
		surveyQn.setSurveyId(1L);
		surveyQn.setVersion(1L);
		surveyQn.setQuestion(null);

		Mockito.when(surveyqnService.patchUpdate(surveyQnRequest, 1L, 1L, 1L)).thenReturn(surveyQn);
		ResponseEntity<SurveyQuestion> survey1 = surveyQnController.patchUpdateSurvey(1L, 1L, 1L, surveyQnRequest);

		Assert.assertEquals(202, survey1.getStatusCode().value());

	}

	@Test
	public void testCBInvalidSurveyId() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.patchUpdateSurvey(null, 1L, 1L, surveyQnRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("surveyId", e.getField());

		}

	}

	@Test
	public void testCCInvalidVersion() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.patchUpdateSurvey(1L, null, 1L, surveyQnRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("version", e.getField());

		}

	}

	@Test
	public void testCDInvalidQuestionId() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.patchUpdateSurvey(1L, 1L, null, surveyQnRequest);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("questionId", e.getField());

		}

	}

	@Test
	public void testDAFindByIdUpdateSuccess() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		Mockito.when(surveyqnService.findById(1L, 1L, 1L)).thenReturn(new Question());
		ResponseEntity<Question> survey1 = surveyQnController.findByQuestionId(1L, 1L, 1L);

		Assert.assertEquals(200, survey1.getStatusCode().value());

	}

	@Test
	public void testDBInvalidSurveyId() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.findByQuestionId(null, 1L, 1L);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("surveyId", e.getField());

		}

	}

	@Test
	public void testDCInvalidVersion() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.findByQuestionId(1L, null, 1L);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("version", e.getField());

		}

	}

	@Test
	public void testDDInvalidQuestionId() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.findByQuestionId(1L, 1L, null);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("questionId", e.getField());

		}

	}

	@Test
	public void testEAfindByUserSuccess() throws Exception {

		Mockito.when(surveyqnService.findAllQuestionBySurvey(1L, 1L)).thenReturn(new SurveyQuestion());
		ResponseEntity<SurveyQuestion> survey1 = surveyQnController.findQuestionsById(1L, 1L);

		Assert.assertEquals(200, survey1.getStatusCode().value());

	}

	@Test
	public void testEBInvalidSurveyId() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.findQuestionsById(1L, 1L);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("surveyId", e.getField());

		}

	}

	@Test
	public void testECInvalidVersion() throws Exception {

		SurveyQuestionDto surveyQnRequest = new SurveyQuestionDto();
		surveyQnRequest.setQnText("Do you like shopping?");
		surveyQnRequest.setQnType("radio");
		surveyQnRequest.setOptionOne("yes");
		surveyQnRequest.setOptionTwo("no");
		surveyQnRequest.setOptionThree("sometimes");

		try {
			surveyQnController.findQuestionsById(1L, 1L);
		} catch (ValidationException e) {
			Assert.assertEquals(400, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), e.getErrorMessage());
			Assert.assertEquals("version", e.getField());

		}

	}
}
