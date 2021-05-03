package com.oracle.surveys.service;

import java.util.List;

import com.oracle.surveys.model.entity.Survey;
import com.oracle.surveys.model.request.SurveyRequest;
import com.oracle.surveys.model.request.SurveyUpdateRequest;
import com.oracle.surveys.model.response.SurveyVersionResponse;

public interface SurveyService {
	
	default Survey create(SurveyRequest request) {return null;};
	default Survey update(SurveyRequest request, Long id) {return null;};
	default Survey patchUpdate(SurveyUpdateRequest request, Long surveyId, Long version) {return null;};
	default SurveyVersionResponse findById(Long id, Long version) {return null;};
	default List<Survey> findAll() {return null;};
	default List<Survey> findAllByUser(Long userId) {return null;};
	default void delete(Long id, String version) {};
	default Survey updateVersion(Long surveyId, SurveyRequest request) {return null;};

}
