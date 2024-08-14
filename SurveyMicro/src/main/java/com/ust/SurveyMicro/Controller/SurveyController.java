package com.ust.SurveyMicro.Controller;

import com.ust.SurveyMicro.Enitity.Survey;
import com.ust.SurveyMicro.Enitity.SurveyDTO;
import com.ust.SurveyMicro.Service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;

    @GetMapping("/{survey_id}")
    public ResponseEntity<SurveyDTO> getSurveyById(@PathVariable("survey_id") Long surveyId) {
        SurveyDTO survey = surveyService.getSurveyById(surveyId);
        return ResponseEntity.ok(survey);
    }

    @GetMapping
    public ResponseEntity<List<SurveyDTO>> getAllSurveys() {
        List<SurveyDTO> surveys = surveyService.getAllSurveys();
        return ResponseEntity.ok(surveys);
    }

    @PostMapping
    public ResponseEntity<String> createSurvey(@RequestBody Survey survey) {
        try {
            Survey createdSurvey = surveyService.createSurvey(survey);

            return new ResponseEntity<>("Survey created successfully", HttpStatus.CREATED);
        }
         catch (Exception e) {

            return new ResponseEntity<>("SetName not found: " + survey.getSetName(), HttpStatus.OK);
        }
    }


}

