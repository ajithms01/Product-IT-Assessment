package com.example.AssessmentMicro.Controller;

import com.example.AssessmentMicro.Entity.Assessment;
import com.example.AssessmentMicro.dto.AssessmentDTO;
import com.example.AssessmentMicro.dto.AnswerDTO;
import com.example.AssessmentMicro.Service.AssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assessment")
public class AssessmentController {

    private final AssessmentService assessmentService;

    // Constructor-based injection
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    // Endpoint to get all assessments
    @GetMapping
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        List<Assessment> assessments = assessmentService.getAllAssessments();
        return ResponseEntity.status(HttpStatus.OK).body(assessments);
    }

    @GetMapping("/{setName}/questions")
    public ResponseEntity<?> getQuestionNamesBySetName(@PathVariable String setName) {
        try {
            List<String> questionNames = assessmentService.getQuestionNamesBySetName(setName);
            return ResponseEntity.status(HttpStatus.OK).body(questionNames);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving question names: " + e.getMessage());
        }
    }

    // Endpoint to get an assessment by its set ID
    @GetMapping("/{setId}")
    public ResponseEntity<?> getAssessmentBySetId(@PathVariable Long setId) {
        try {
            Optional<AssessmentDTO> assessment = assessmentService.getAssessmentBySetId(setId);
            return assessment.isPresent()
                    ? ResponseEntity.status(HttpStatus.OK).body(assessment.get())
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Assessment not found with set ID: " + setId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving assessment: " + e.getMessage());
        }
    }

    // Endpoint to create a new assessment
    @PostMapping
    public ResponseEntity<?> createAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        try {
            AssessmentDTO createdAssessment = assessmentService.createAssessment(assessmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAssessment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating assessment: " + e.getMessage());
        }
    }

    @PutMapping("/{setId}/questions/{questionId}")
    public ResponseEntity<?> updateQuestionOptions(@PathVariable Long setId, @PathVariable Long questionId,
                                                   @RequestBody List<AnswerDTO> answerDTOs) {
        try {
            assessmentService.updateQuestionOptions(setId, questionId, answerDTOs);
            return ResponseEntity.status(HttpStatus.OK).body("Question options updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating question options: " + e.getMessage());
        }
    }

    @DeleteMapping("/{setId}/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long setId, @PathVariable Long questionId) {
        try {
            assessmentService.deleteQuestion(setId, questionId);
            return ResponseEntity.status(HttpStatus.OK).body("Question deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting question: " + e.getMessage());
        }
    }
}
