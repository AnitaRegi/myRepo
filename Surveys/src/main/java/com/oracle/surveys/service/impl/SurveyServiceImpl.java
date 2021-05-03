package com.oracle.surveys.service.impl;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.oracle.surveys.constants.ErrorCodes;
import com.oracle.surveys.constants.ErrorMessages;
import com.oracle.surveys.exception.SurveyException;
import com.oracle.surveys.model.entity.Survey;
import com.oracle.surveys.model.entity.SurveyVersion;
import com.oracle.surveys.model.request.SurveyRequest;
import com.oracle.surveys.model.request.SurveyUpdateRequest;
import com.oracle.surveys.model.response.SurveyVersionResponse;
import com.oracle.surveys.repo.SurveyRepository;
import com.oracle.surveys.repo.SurveyVersionRepository;
import com.oracle.surveys.service.SurveyService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	SurveyRepository surveyRepo;

	@Autowired
	SurveyVersionRepository surveyVersionRepo;

	@Override
	public Survey create(SurveyRequest request) {

		log.debug("SurveyServiceImpl.create() Entered...");

		Survey survey = request.toEntity();
		survey = surveyRepo.save(survey);

		SurveyVersion version = new SurveyVersion();
		version.setName(request.getName());
		version.setDescription(request.getDescription());
		version.setVersion(1L);
		version.setStatus("inactive");
		version.setStartDate(request.getStartDate());
		version.setEndDate(request.getEndDate());
		version.setSurvey(survey);
	
		survey.addVersions(version);
		survey.setUpdatedOn(new Timestamp(System.currentTimeMillis())); 

		log.debug("SurveyServiceImpl.create() Exited...");
		
		return surveyRepo.save(survey);
	}

	
	public Survey updateVersion(Long id, SurveyRequest request) {
		
		log.debug("SurveyServiceImpl.updateVersion() Entered...");

		Survey survey = surveyRepo.findById(id)
				.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(),
						ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		SurveyVersion version = new SurveyVersion();
		
		Long lastVersion = survey.getVersions().stream().max(Comparator.comparing(SurveyVersion::getVersion)).get()
				.getVersion();
		version.setVersion(lastVersion + 1);
		version.setName(request.getName());
		version.setDescription(request.getDescription());
		version.setStatus("inactive");
		version.setStartDate(request.getStartDate());
		version.setEndDate(request.getEndDate());
		version.setSurvey(survey);
		survey.addVersions(version);
		survey.setUpdatedOn(new Timestamp(System.currentTimeMillis())); 
		survey.setUpdatedBy(request.getUserId()); 

		log.debug("SurveyServiceImpl.updateVersion() Entered...");

		return surveyRepo.save(survey);
	}

	@Override
	public Survey patchUpdate(SurveyUpdateRequest request, Long surveyId, Long version) {

		log.debug("SurveyServiceImpl.patchUpdate() Entered...");
		Optional<Survey> surveyOpt = surveyRepo.findById(surveyId);
		Survey survey = surveyOpt.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(),
				ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		Optional<SurveyVersion> surveyVersionOpt = survey.getVersions().stream()
				.filter(ver -> ver.getVersion().equals(version)).findAny();

		SurveyVersion surveyVersion = surveyVersionOpt
				.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEY_VERSION_DOESNT_EXIST.getValue(),
						ErrorMessages.SURVEY_VERSION_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		if (StringUtils.isNotBlank(request.getName())) {
			surveyVersion.setName(request.getName());
		}
		if (StringUtils.isNotBlank(request.getDescription())) {
			surveyVersion.setDescription(request.getDescription());
		}

		if (!ObjectUtils.isEmpty(request.getStartDate())) {
			surveyVersion.setStartDate(request.getStartDate());
		}
		if (!ObjectUtils.isEmpty(request.getEndDate())) {
			surveyVersion.setEndDate(request.getEndDate());
		}
		
		if (StringUtils.isNotBlank(request.getStatus())) {
			surveyVersion.setStatus(request.getStatus().toLowerCase());
		}

		survey.addVersions(surveyVersion);
		survey.setUpdatedOn(new Timestamp(System.currentTimeMillis())); 
		survey.setUpdatedBy(request.getUserId()); 
		
		log.debug("SurveyServiceImpl.patchUpdate() Exited...");

		return surveyRepo.save(survey);

	}

	@Override
	public SurveyVersionResponse findById(Long id, Long version) {

		log.debug("SurveyServiceImpl.findById() Entered...");

		Optional<Survey> surveyOpt = surveyRepo.findById(id);
		Survey survey = surveyOpt.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEY_DOESNT_EXIST.getValue(),
				ErrorMessages.SURVEY_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		Optional<SurveyVersion> surveyVersionOpt = survey.getVersions().stream()
				.filter(ver -> ver.getVersion().equals(version)).findAny();

		SurveyVersion surveyVersion = surveyVersionOpt
				.orElseThrow(() -> new SurveyException(ErrorCodes.SURVEY_VERSION_DOESNT_EXIST.getValue(),
						ErrorMessages.SURVEY_VERSION_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		log.debug("SurveyServiceImpl.findById() Exited...");

		return new SurveyVersionResponse(id, version, surveyVersion.getName(), surveyVersion.getDescription(),
				surveyVersion.getStartDate(), surveyVersion.getEndDate(), surveyVersion.getStatus());

	}

	@Override
	public List<Survey> findAllByUser(Long userId) {

		log.debug("SurveyServiceImpl.findAllByUser() Entered...");
		List<Survey> surveys = surveyRepo.findSurveyByCreatedBy(userId);
		log.debug("SurveyServiceImpl.findAllByUser() Exited...");

		return surveys;
	}

}
