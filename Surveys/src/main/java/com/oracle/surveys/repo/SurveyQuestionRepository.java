package com.oracle.surveys.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oracle.surveys.model.SurveyVersionPk;
import com.oracle.surveys.model.entity.SurveyQuestion;

@Repository
public interface SurveyQuestionRepository extends CrudRepository<SurveyQuestion,SurveyVersionPk>{
}
