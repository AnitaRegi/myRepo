package com.oracle.surveys.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oracle.surveys.model.entity.UserResponse;

@Repository
public interface UserSurveyRepository extends CrudRepository<UserResponse, Long> {
	List<UserResponse> findByUserIdAndCompleted(Long userId, Boolean completed);
	
	Optional<UserResponse> findByUserIdAndSurveyIdAndVersion(Long userId,Long surveyId, Long version);

	List<UserResponse> findBySurveyIdAndVersion(Long surveyId, Long version);
}
