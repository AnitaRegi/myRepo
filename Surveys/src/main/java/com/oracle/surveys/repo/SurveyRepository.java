package com.oracle.surveys.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oracle.surveys.model.entity.Survey;

@Repository
public interface SurveyRepository extends CrudRepository<Survey,Long>{
	Survey findSurveyByNameAndCreatedBy( String name, Long userId);
	Optional<Survey> findBySurveyIdAndVersion(Long userId, String version);
}
