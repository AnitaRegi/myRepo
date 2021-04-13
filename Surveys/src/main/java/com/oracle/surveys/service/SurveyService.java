package com.oracle.surveys.service;

import java.util.List;

import com.oracle.surveys.model.SurveyDto;
import com.oracle.surveys.model.entity.Survey;

public interface SurveyService {
	
	Survey create(SurveyDto request);
	Survey update(SurveyDto request, Long id);
	Survey patchUpdate(SurveyDto request, Long id);
	Survey findById(Long id, String version);
	List<Survey> findAll();
	List<Survey> findAllByUser(Long userId);
	void delete(Long id, String version);

}
