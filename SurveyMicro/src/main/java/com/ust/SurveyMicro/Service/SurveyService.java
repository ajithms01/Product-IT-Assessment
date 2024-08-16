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

import java.util.*;
import java.util.stream.Collectors;

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

    public List<SurveyDTO> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        List<SurveyDTO> surveyDTOs = new ArrayList<>();

        for (Survey survey : surveys) {
            try {
                // Fetch the assessment details for each survey
                ResponseEntity<Assessment> response = assessmentClient.getAssessmentBySetName(survey.getSetName());
                if (response.getStatusCode() == HttpStatus.OK) {
                    Assessment assessment = response.getBody();
                    List<Question> allQuestions = assessment.getQuestions();

                    // Convert the comma-separated questionIds string to a List<Long>
                    String questionIdsString = survey.getQuestionIds(); // Assuming this is the comma-separated string
                    List<Long> questionIds = Arrays.stream(questionIdsString.split(","))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());

                    // Filter the questions based on the questionIds
                    List<Question> filteredQuestions = allQuestions.stream()
                            .filter(question -> questionIds.contains(question.getQuestionId()))
                            .collect(Collectors.toList());

                    // Create the SurveyDTO object
                    SurveyDTO surveyDTO = new SurveyDTO();
                    surveyDTO.setQuestions(filteredQuestions);
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


        public ResponseEntity<String> createSurvey (Survey survey){
            try {
                // Retrieve assessment based on setName
                System.out.println(survey);
                ResponseEntity<Assessment> response = assessmentClient.getAssessmentBySetName(survey.getSetName());

                if (response.getStatusCode() == HttpStatus.OK) {
                    Assessment assessment = response.getBody();
                    List<Question> allQuestions = assessment.getQuestions();

                    // Convert the comma-separated questionIds string to a List<Long>
                    String questionIdsString = survey.getQuestionIds(); // Assuming this is the comma-separated string
                    List<Long> providedQuestionIds = Arrays.stream(questionIdsString.split(","))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());

                    // Filter the questions based on provided IDs
                    List<Question> selectedQuestions = allQuestions.stream()
                            .filter(question -> providedQuestionIds.contains(question.getQuestionId()))
                            .collect(Collectors.toList());

                    // Check if all provided question IDs are found
                    if (selectedQuestions.size() != providedQuestionIds.size()) {
                        return new ResponseEntity<>("One or more question IDs not found", HttpStatus.OK);
                    }

                    // Convert the list of Long IDs back to a comma-separated string
                    String updatedQuestionIds = selectedQuestions.stream()
                            .map(question -> String.valueOf(question.getQuestionId()))
                            .collect(Collectors.joining(","));
                    // Set the questionIds back to the survey object
                    survey.setQuestionIds(updatedQuestionIds);

                    // Save the Survey object
                    surveyRepository.save(survey);
                    return new ResponseEntity<>("Survey created successfully", HttpStatus.CREATED);

                } else {
                    // Assessment not found for setName
                    return new ResponseEntity<>("Assessment not found for set name: " + survey.getSetName(), HttpStatus.OK);
                }

            } catch (Exception e) {
                // Handle unexpected exceptions
                return new ResponseEntity<>("Set name or Question Id not found", HttpStatus.OK);
            }


    }
}



