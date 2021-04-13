package com.oracle.surveys.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oracle.surveys.model.entity.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Long>{
	
}
