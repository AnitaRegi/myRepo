package com.oracle.surveys.service;

import java.time.LocalDate;

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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.oracle.surveys.SurveysApplication;
import com.oracle.surveys.constants.ErrorCodes;
import com.oracle.surveys.constants.ErrorMessages;
import com.oracle.surveys.exception.SurveyException;
import com.oracle.surveys.model.request.SurveyRequest;
import com.oracle.surveys.model.request.SurveyUpdateRequest;
import com.oracle.surveys.repo.SurveyRepository;
import com.oracle.surveys.repo.SurveyVersionRepository;
import com.oracle.surveys.service.impl.SurveyServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SurveysApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SurveyControllerTests {

	@Autowired
	private SurveyService surveyService;

	@Mock
	SurveyRepository surveyRepo;

	@Mock
	SurveyVersionRepository surveyVersionRepo;


	@Before
	public void init() {
		surveyService = new SurveyServiceImpl();
		MockitoAnnotations.openMocks(this);
		
		surveyRepo = Mockito.mock(surveyRepo.getClass());
		surveyVersionRepo = Mockito.mock(surveyVersionRepo.getClass());

		ReflectionTestUtils.setField(surveyService, "surveyRepo", surveyRepo);
		ReflectionTestUtils.setField(surveyService, "surveyVersionRepo", surveyVersionRepo);

	}

	

	@Test
	public void testUpdateNoSurvey() throws Exception {

		SurveyRequest surveyRequest = new SurveyRequest();
		surveyRequest.setUserId(1L);
		surveyRequest.setName("Test Survey");
		surveyRequest.setDescription("Test Survey");
		surveyRequest.setStartDate(LocalDate.now());
		surveyRequest.setEndDate(LocalDate.now());

		Mockito.when(surveyRepo.findById(1L)).thenThrow(new SurveyException(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(),
				ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));
		try {
		 surveyService.updateVersion(1L, surveyRequest);
		}catch (SurveyException e) {
			Assert.assertEquals(404, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), e.getErrorMessage());

		}

	}

	
	@Test
	public void testPatchUpdateNoSurvey() throws Exception {

		SurveyUpdateRequest surveyRequest = new SurveyUpdateRequest();
		surveyRequest.setUserId(1L);
		surveyRequest.setName("Test Survey");
		surveyRequest.setDescription("Test Survey");
		surveyRequest.setStartDate(LocalDate.now());
		surveyRequest.setEndDate(LocalDate.now());

		Mockito.when(surveyRepo.findById(1L)).thenThrow(new SurveyException(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(),
				ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));
		try {
		 surveyService.updateVersion(1L, surveyRequest);
		}catch (SurveyException e) {
			Assert.assertEquals(404, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), e.getErrorMessage());

		}

	}
	
	@Test
	public void testFindByIdNoSurvey() throws Exception {

		SurveyUpdateRequest surveyRequest = new SurveyUpdateRequest();
		surveyRequest.setUserId(1L);
		surveyRequest.setName("Test Survey");
		surveyRequest.setDescription("Test Survey");
		surveyRequest.setStartDate(LocalDate.now());
		surveyRequest.setEndDate(LocalDate.now());

		Mockito.when(surveyRepo.findById(1L)).thenThrow(new SurveyException(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(),
				ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));
		try {
		 surveyService.updateVersion(1L, surveyRequest);
		}catch (SurveyException e) {
			Assert.assertEquals(404, e.getHttpStatusCode().value());
			Assert.assertEquals(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(), e.getErrorCode());
			Assert.assertEquals(ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), e.getErrorMessage());

		}

	}


}
