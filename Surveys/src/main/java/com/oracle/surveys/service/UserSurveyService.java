package com.oracle.surveys.service;

import java.util.List;

import com.oracle.surveys.model.entity.SurveyQuestion;
import com.oracle.surveys.model.entity.SurveyVersion;
import com.oracle.surveys.model.entity.UserResponse;
import com.oracle.surveys.model.request.UserSurveyPatchRequest;
import com.oracle.surveys.model.request.UserSurveyRequest;

public interface UserSurveyService {
	
	default UserResponse create(Long userId, Long surveyId, Long version, UserSurveyRequest request) {return null;};
    default UserResponse patchUpdate(UserSurveyPatchRequest request) {return null; };
    default UserResponse findById(Long userId, Long surveyId, Long version) {return null; };
	default List<SurveyVersion> findActiveSurveys(){return null;};
	default List<SurveyQuestion> findPendingSurveys(Long userId){return null;};
	default List<UserResponse> findAllBySurvey(Long surveyId, Long version){return null;};

}
