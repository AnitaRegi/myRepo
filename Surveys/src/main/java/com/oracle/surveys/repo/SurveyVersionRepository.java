package com.oracle.surveys.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oracle.surveys.model.entity.SurveyVersion;

@Repository
public interface SurveyVersionRepository extends CrudRepository<SurveyVersion,Long>{
	
	Optional<SurveyVersion> findBySurveyIdAndVersion(Long surveyId, Long version);
	List<SurveyVersion> findByStatus(String status);

}
