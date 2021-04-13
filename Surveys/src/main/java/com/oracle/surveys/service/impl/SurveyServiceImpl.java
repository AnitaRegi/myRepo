package com.oracle.surveys.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.oracle.surveys.constants.ErrorCodes;
import com.oracle.surveys.constants.ErrorMessages;
import com.oracle.surveys.exception.SurveyException;
import com.oracle.surveys.model.SurveyDto;
import com.oracle.surveys.model.entity.Survey;
import com.oracle.surveys.repo.SurveyRepository;
import com.oracle.surveys.service.SurveyService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	SurveyRepository surveyRepo;

	@Override
	public Survey create(SurveyDto request) {
		
		log.debug("SurveyServiceImpl.create() Entered...");
		Survey survey = request.toEntity();
		log.debug("SurveyServiceImpl.create() Exited...");

		return surveyRepo.save(survey);
	}

	@Override
	public Survey update(SurveyDto request, Long id) {
		
		log.debug("SurveyServiceImpl.update() Entered...");
		Survey survey = findById(id,request.getVersion());
		Survey updatedSurvey = request.toEntity();
		updatedSurvey.setSurveyId(survey.getSurveyId());
		log.debug("SurveyServiceImpl.update() Exited...");
		return surveyRepo.save(updatedSurvey);
		
	}

	@Override
	public Survey patchUpdate(SurveyDto request, Long id) {
		
		log.debug("SurveyServiceImpl.patchUpdate() Entered...");
		Survey survey = findById(id,request.getVersion());
		if (StringUtils.isNotBlank(request.getName())) {
			survey.setName(request.getName());
		}
		if (StringUtils.isNotBlank(request.getDescription())) {
			survey.setDescription(request.getDescription());
		}
		if (StringUtils.isNotBlank(request.getVersion())) {
			survey.setVersion(request.getVersion());
		}
		/*if (ObjectUtils.isNotEmpty(request.getStartDate())) {
			survey.setStartDate(new Date(request.getStartDate()));
		}
		if (ObjectUtils.isNotEmpty(request.getEndDate())) {
			survey.setEndDate(new Date(request.getEndDate()));
		}*/
		if (StringUtils.isNotBlank(request.getStatus())) {
			survey.setStatus(request.getStatus());
		}	
		log.debug("SurveyServiceImpl.patchUpdate() Exited...");

		return surveyRepo.save(survey);
	}

	@Override
	public Survey findById(Long id,String version) {
		
		log.debug("SurveyServiceImpl.findById() Entered...");
		Optional<Survey> surveyOpt = surveyRepo.findById(id);
		Survey survey = surveyOpt
				.map(obj -> { return surveyOpt.get();})
				.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(),
						ErrorMessages.SURVEY_DOESNT_EXIST.getValue() , HttpStatus.NOT_FOUND));
		log.debug("SurveyServiceImpl.findById() Exited...");

		return survey;
	}

	@Override
	public List<Survey> findAll() {
		
		log.debug("Inside SurveyServiceImpl.findAll()...");
		return (List<Survey>) surveyRepo.findAll();

	}

	@Override
	public void delete(Long id,String version) {
		
		log.debug("Inside SurveyServiceImpl.delete()...");
		surveyRepo.deleteById(id);
	}

	@Override
	public List<Survey> findAllByUser(Long userId) {
		
		log.debug("SurveyServiceImpl.findAllByUser() Entered...");
		List<Survey> surveys = findAll();
		if (!CollectionUtils.isEmpty(surveys)) {
			surveys = surveys.stream()
					.filter(survey -> survey.getCreatedBy() == userId)
					.collect(Collectors.toList());
		}
		log.debug("SurveyServiceImpl.findAllByUser() Exited...");
		
		return surveys;
	}

}
