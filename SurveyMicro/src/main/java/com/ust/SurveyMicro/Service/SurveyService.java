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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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


            ResponseEntity<Assessment> response = assessmentClient.getAssessmentBySetName(survey.getSetName());
            if (response.getStatusCode() == HttpStatus.OK) {

                Assessment assessment = response.getBody();

            } else {
                throw new NoSuchElementException("Assessment not found for set name: " + survey.getSetName());
            }

            return surveyRepository.save(survey);

        }
    }







