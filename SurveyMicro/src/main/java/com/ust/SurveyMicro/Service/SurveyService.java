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
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private AssessmentClient assessmentClient;


    // Method to get a survey by its ID
    public SurveyDTO getSurveyById(String surveyId) {
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        ResponseEntity<Assessment> qs = assessmentClient.getAssessmentBySetName(survey.get().getSetName());
        if (qs.getStatusCode() == HttpStatus.OK) {
            Assessment assessment = qs.getBody();
            List<Question> questions = assessment.getQuestions();
            System.out.println(questions);
            SurveyDTO surveyDTO = new SurveyDTO();
            surveyDTO.setQuestions(questions);
            surveyDTO.setSurveyId(surveyId);
            surveyDTO.setCompanyName(survey.get().getCompanyName());
            surveyDTO.setStatus(survey.get().getStatus());
            surveyDTO.setEmail(survey.get().getEmail());
            surveyDTO.setDomainName(survey.get().getDomainName());
            surveyDTO.setSetName(survey.get().getSetName());
            return surveyDTO;

        }
        return null;

    }

    // Method to get all surveys
    public List<SurveyDTO> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        List<SurveyDTO> surveyDTOs = new ArrayList<>();

        for (Survey survey : surveys) {
            // Fetch the assessment details for each survey
            ResponseEntity<Assessment> qs = assessmentClient.getAssessmentBySetName(survey.getSetName());
            if (qs.getStatusCode() == HttpStatus.OK) {
                Assessment assessment = qs.getBody();
                List<Question> questions = assessment.getQuestions();

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
            }
        }
        return surveyDTOs;
    }


    // Method to create a new survey
    public Survey createSurvey(Survey survey) {
        // Retrieve questions and options using AssessmentClient and add them to the survey
        ResponseEntity<Assessment> response = assessmentClient.getAssessmentBySetName(survey.getSetName());
        if (response.getStatusCode() == HttpStatus.OK) {
            Assessment assessment = response.getBody();

        }
        return surveyRepository.save(survey);
    }

    // Method to update an existing survey



}