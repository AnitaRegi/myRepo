package com.oracle.surveys.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.oracle.surveys.constants.ErrorCodes;
import com.oracle.surveys.constants.ErrorMessages;
import com.oracle.surveys.exception.SurveyException;
import com.oracle.surveys.model.SurveyVersionPk;
import com.oracle.surveys.model.entity.SurveyQuestion;
import com.oracle.surveys.model.entity.SurveyVersion;
import com.oracle.surveys.model.entity.UserAnswer;
import com.oracle.surveys.model.entity.UserResponse;
import com.oracle.surveys.model.request.UserSurveyPatchRequest;
import com.oracle.surveys.model.request.UserSurveyRequest;
import com.oracle.surveys.repo.SurveyQuestionRepository;
import com.oracle.surveys.repo.SurveyVersionRepository;
import com.oracle.surveys.repo.UserSurveyRepository;
import com.oracle.surveys.service.UserSurveyService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserSurveyServiceImpl implements UserSurveyService {

	@Autowired
	UserSurveyRepository userSurveyRepo;

	@Autowired
	SurveyVersionRepository surveyVersionRepo;

	@Autowired
	SurveyQuestionRepository surveyQnRepo;

	/**
	 * Adds questions to the specified survey version.
	 */
	@Override
	public UserResponse create(Long userId, Long surveyId, Long version, UserSurveyRequest request) {

		log.debug("UserSurveyServiceImpl.create() Entered...");

		UserResponse userResponse = userSurveyRepo.findByUserIdAndSurveyIdAndVersion(userId, surveyId, version)
				.orElse(new UserResponse());
		
		userResponse.setUserId(userId);
		userResponse.setSurveyId(surveyId);
		userResponse.setVersion(version);
		userResponse.setCompleted(null == request.getCompleted() ? false : request.getCompleted());
		List<UserAnswer> dbAns = userResponse.getAnswers();
		for (UserAnswer userAnswer : request.getUserAnswer()) {
			Optional<UserAnswer> alreadyAnswered = dbAns.stream()
					.filter(ans -> ans.getQuestionId().equals(userAnswer.getQuestionId())).findAny();

			if (alreadyAnswered.isPresent()) {
				dbAns.remove(alreadyAnswered.get());
			}
			dbAns.add(userAnswer);

		}

		request.getUserAnswer().forEach(ans -> ans.setUserResponse(userResponse));
		log.debug("UserSurveyServiceImpl.create() Exited...");

		return userSurveyRepo.save(userResponse);
	}

	@Override
	public List<SurveyVersion> findActiveSurveys() {
		log.debug("Inside UserSurveyServiceImpl.findActiveSurveys() entered...");

		List<SurveyVersion> surveys = surveyVersionRepo.findByStatus("active");
		System.out.println(surveys.size());
		System.out.println(LocalDate.now());

		surveys.forEach(s -> System.out.println(s.getId() + " : " + LocalDate.now().isAfter(s.getStartDate())));
		surveys = surveys.stream().filter(survey -> LocalDate.now().isAfter(survey.getStartDate())
				&& LocalDate.now().isBefore(survey.getEndDate())).collect(Collectors.toList());

		log.debug("Inside UserSurveyServiceImpl.findActiveSurveys() exited...");

		return surveys;

	}

	@Override
	public List<SurveyQuestion> findPendingSurveys(Long userId) {
		log.debug("Inside UserSurveyServiceImpl.findActiveSurveys() entered...");

		List<UserResponse> response = userSurveyRepo.findByUserIdAndCompleted(userId, false);
		List<SurveyVersionPk> surveyPks = new ArrayList<>();
		for (UserResponse resp : response) {
			surveyPks.add(new SurveyVersionPk(resp.getSurveyId(), resp.getVersion()));
		}

		List<SurveyQuestion> surveyQuestions = (List<SurveyQuestion>) surveyQnRepo.findAllById(surveyPks);

		log.debug("Inside UserSurveyServiceImpl.findActiveSurveys() exited...");

		return surveyQuestions;

	}

	@Override
	public UserResponse patchUpdate(UserSurveyPatchRequest request) {

		log.debug("UserSurveyServiceImpl.patchUpdate() Entered...");

		UserResponse response = userSurveyRepo.findById(request.getUserResponseId())
				.orElseThrow(() -> new SurveyException(ErrorCodes.USR_RESPONSE_DOESNT_EXIST.getValue(),
						ErrorMessages.USR_RESPONSE_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));
		if (null != request.getCompleted()) {
			response.setCompleted(request.getCompleted());
		}
		log.debug("UserSurveyServiceImpl.patchUpdate() Exited...");

		return userSurveyRepo.save(response);
	}

	@Override
	public UserResponse findById(Long userId, Long surveyId, Long version) {
		log.debug("UserSurveyServiceImpl.findById() Entered...");

		UserResponse response = userSurveyRepo.findByUserIdAndSurveyIdAndVersion(userId, surveyId, version)
				.orElseThrow(() -> new SurveyException(ErrorCodes.USR_RESPONSE_DOESNT_EXIST.getValue(),
						ErrorMessages.USR_RESPONSE_DOESNT_EXIST.getValue(), HttpStatus.NOT_FOUND));

		log.debug("UserSurveyServiceImpl.findById() Entered...");

		return response;

	}
	
	
	@Override
	public List<UserResponse> findAllBySurvey(Long surveyId, Long version) {
		log.debug("UserSurveyServiceImpl.findAllBySurvey() Entered...");

		List<UserResponse> response = userSurveyRepo.findBySurveyIdAndVersion(surveyId, version);

		log.debug("UserSurveyServiceImpl.findAllBySurvey() Entered...");

		return response;

	}

}