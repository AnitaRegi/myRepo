package com.oracle.surveys.validation.service;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;

import com.oracle.surveys.constants.ErrorCodes;
import com.oracle.surveys.constants.ErrorMessages;
import com.oracle.surveys.exception.ValidationException;
import com.oracle.surveys.model.SurveyPk;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Anita
 *
 * Class that contains validations for surveyQuestionId fields.
 */
@Slf4j
public class SurveyQuestionIdValidator {
	public static void validate(SurveyPk id) {
		
		log.debug("SurveyQuestionIdValidator.validate() Entered...");

	/*	if(ObjectUtils.isEmpty(id.getQuestionId())) {
			throw new ValidationException(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(),
					ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), "questionId", HttpStatus.BAD_REQUEST);
		}*/
		/*
		if(ObjectUtils.isEmpty(id.getSurveyId())) {
			throw new ValidationException(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(),
					ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), "surveyId", HttpStatus.BAD_REQUEST);
		}
		
		if(ObjectUtils.isEmpty(id.getVersion())) {
			throw new ValidationException(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(),
					ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), "version", HttpStatus.BAD_REQUEST);
		}
		*/
		log.debug("SurveyQuestionIdValidator.validate() Exited...");

		
	}

}
