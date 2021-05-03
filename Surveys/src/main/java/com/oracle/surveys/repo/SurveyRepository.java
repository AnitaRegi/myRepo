package com.oracle.surveys.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oracle.surveys.model.entity.Survey;

@Repository
public interface SurveyRepository extends CrudRepository<Survey,Long>{
	List<Survey> findSurveyByCreatedBy(Long userId);
	//Optional<Survey> findBySurveyIdAndVersion(Long userId, Long version);
}
