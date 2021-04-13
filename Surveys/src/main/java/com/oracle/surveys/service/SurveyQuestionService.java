package com.oracle.surveys.service;

import java.util.List;

import com.oracle.surveys.model.SurveyQuestionDto;
import com.oracle.surveys.model.entity.Question;
import com.oracle.surveys.model.entity.SurveyQuestion;

public interface SurveyQuestionService {
	
    SurveyQuestion create(Long surveyId, SurveyQuestionDto request);
    default SurveyQuestion update(SurveyQuestionDto request,Long surveyId, Long questionId) {return null;};
    default SurveyQuestion patchUpdate(SurveyQuestionDto request, Long surveyId, Long questionId){return null;};
    default SurveyQuestion findById(Long surveyId, String version){return null;};
    default List<SurveyQuestion> findAll(){return null;};
    default void delete(Long surveyId, String version, Long questionId ) {}
	default Question findByQuestionId(Long surveyId, String version, Long questionId) {return null;};

}
