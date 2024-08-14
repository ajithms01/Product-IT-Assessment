package com.ust.SurveyMicro.Service;

import com.ust.SurveyMicro.Enitity.Assessment;
import com.ust.SurveyMicro.Enitity.Question;
import com.ust.SurveyMicro.Enitity.Survey;
import com.ust.SurveyMicro.Enitity.SurveyDTO;
import com.ust.SurveyMicro.FeignClient.AssessmentClient;
import com.ust.SurveyMicro.Repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SurveyService {

    private static final Logger logger = LoggerFactory.getLogger(SurveyService.class);


    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private AssessmentClient assessmentClient;

    // Method to get a survey by its ID
    public SurveyDTO getSurveyById(Long surveyId) {
        // Retrieve survey from the repository
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new NoSuchElementException("Survey not found with ID: " + surveyId));

        // Retrieve the assessment details using Feign client
        ResponseEntity<Assessment> response = assessmentClient.getAssessmentBySetName(survey.getSetName());
        if (response.getStatusCode() == HttpStatus.OK) {
            Assessment assessment = response.getBody();
            List<Question> questions = assessment.getQuestions();

            // Create and return SurveyDTO
            SurveyDTO surveyDTO = new SurveyDTO();
            surveyDTO.setQuestions(questions);
            surveyDTO.setSurveyId(surveyId);
            surveyDTO.setCompanyName(survey.getCompanyName());
            surveyDTO.setStatus(survey.getStatus());
            surveyDTO.setEmail(survey.getEmail());
            surveyDTO.setDomainName(survey.getDomainName());
            surveyDTO.setSetName(survey.getSetName());

            return surveyDTO;
        } else {
            throw new NoSuchElementException("Assessment not found for set name: " + survey.getSetName());
        }
    }

    // Method to get all surveys






        public List<SurveyDTO> getAllSurveys() {
            List<Survey> surveys = surveyRepository.findAll();
            List<SurveyDTO> surveyDTOs = new ArrayList<>();

            for (Survey survey : surveys) {
                try {
                    // Fetch the assessment details for each survey
                    ResponseEntity<Assessment> response = assessmentClient.getAssessmentBySetName(survey.getSetName());
                    if (response.getStatusCode() == HttpStatus.OK) {
                        Assessment assessment = response.getBody();
                        List<Question> questions = assessment.getQuestions();

                        // Log the assessment details
//                        logger.info("Fetched assessment: {}", assessment);
//
//                        for (Question question : questions) {
//                            // Log each question to check if optionId and questionId are null
//                            logger.info("Question: {}", question);
//                        }

                        // Create SurveyDTO and set the values
                        SurveyDTO surveyDTO = new SurveyDTO();
                        surveyDTO.setQuestions(questions);
                        surveyDTO.setSurveyId(survey.getSurveyId());
                        surveyDTO.setCompanyName(survey.getCompanyName());
                        surveyDTO.setStatus(survey.getStatus());
                        surveyDTO.setEmail(survey.getEmail());
                        surveyDTO.setDomainName(survey.getDomainName());
                        surveyDTO.setSetName(survey.getSetName());

                        // Add to list of SurveyDTOs
                        surveyDTOs.add(surveyDTO);
                    } else {
                        // Log the issue and continue with the next survey
                        logger.error("Assessment not found for set name: {}", survey.getSetName());
                    }
                } catch (Exception e) {
                    // Log the exception and continue with the next survey
                    logger.error("Error fetching assessment for survey ID: {} - {}", survey.getSurveyId(), e.getMessage());
                }
            }
            return surveyDTOs;
        }



    // Method to create a new survey
    public Survey createSurvey(Survey survey) {
        // Retrieve questions and options using AssessmentClient and add them to the survey
        ResponseEntity<Assessment> response = assessmentClient.getAssessmentBySetName(survey.getSetName());
        if (response.getStatusCode() == HttpStatus.OK) {
            // Here you can include logic to manipulate the assessment if needed.
            Assessment assessment = response.getBody();
            // You can add questions to survey if needed based on the assessment data.
        } else {
            throw new NoSuchElementException("Assessment not found for set name: " + survey.getSetName());
        }
        return surveyRepository.save(survey);
    }

    // Method to update an existing survey
//    public Survey updateSurvey(Long surveyId, Survey updatedSurvey) {
//        Survey existingSurvey = surveyRepository.findById(surveyId)
//                .orElseThrow(() -> new NoSuchElementException("Survey not found with ID: " + surveyId));
//
//        // Update the survey details
//        existingSurvey.setCompanyName(updatedSurvey.getCompanyName());
//        existingSurvey.setStatus(updatedSurvey.getStatus());
//        existingSurvey.setEmail(updatedSurvey.getEmail());
//        existingSurvey.setDomainName(updatedSurvey.getDomainName());
//        existingSurvey.setSetName(updatedSurvey.getSetName());
//
//        return surveyRepository.save(existingSurvey);
//    }
}
