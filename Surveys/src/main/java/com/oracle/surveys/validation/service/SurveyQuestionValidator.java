package com.oracle.surveys.validation.service;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import com.oracle.surveys.constants.ErrorCodes;
import com.oracle.surveys.constants.ErrorMessages;
import com.oracle.surveys.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Anita
 *
 *         Class that contains validations for surveyQuestionId fields.
 */
@Slf4j
public class SurveyQuestionValidator {


	public static void validateSurveyId(Long surveyId) {

		log.debug("SurveyQuestionValidator.validateSurveyId() Entered...");

		if (ObjectUtils.isEmpty(surveyId)) {
			throw new ValidationException(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(),
					ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), "surveyId", HttpStatus.BAD_REQUEST);
		}

		log.debug("SurveyQuestionValidator.validateSurveyId() Exited...");

	}

	public static void validateQuestionId(Long questionId) {

		log.debug("SurveyQuestionValidator.validateQuestionId() Entered...");

		if (ObjectUtils.isEmpty(questionId)) {
			throw new ValidationException(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(),
					ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), "questionId", HttpStatus.BAD_REQUEST);
		}

		log.debug("SurveyQuestionValidator.validateQuestionId() Exited...");

	}

	public static void validateVersion(Long version) {
		log.debug("SurveyQuestionValidator.validateVersion() Entered...");

		if (ObjectUtils.isEmpty(version)) {
			throw new ValidationException(ErrorCodes.REQUIRED_PARAM_MISSING.getValue(),
					ErrorMessages.REQUIRED_PARAM_MISSING.getValue(), "version", HttpStatus.BAD_REQUEST);
		}

		log.debug("SurveyQuestionValidator.validateVersion() Exited...");

	}

}
