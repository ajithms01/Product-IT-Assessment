package com.example.AssessmentMicro.Controller;

import com.example.AssessmentMicro.Entity.Assessment;
import com.example.AssessmentMicro.Service.AssessmentService;
import com.example.AssessmentMicro.dto.AnswerDTO;
import com.example.AssessmentMicro.dto.AssessmentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assessment")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAssessments() {
        List<Assessment> assessments = assessmentService.getAllAssessments();
        return ResponseEntity.ok(assessments);
    }

    @GetMapping("/{setName}/questions")
    public ResponseEntity<?> getQuestionNamesBySetName(@PathVariable String setName) {
        List<String> questionNames = assessmentService.getQuestionNamesBySetName(setName);
        return ResponseEntity.ok(questionNames);
    }

    @GetMapping("/{setId}")
    public ResponseEntity<?> getAssessmentBySetId(@PathVariable Long setId) {
        Optional<AssessmentDTO> assessment = assessmentService.getAssessmentBySetId(setId);
        return ResponseEntity.ok(assessment);
    }

    @GetMapping("/bysetname/{setName}")
    public ResponseEntity<?> getAssessmentBySetName(@PathVariable String setName) {
        Optional<Assessment> assessment = assessmentService.getAssessmentBySetName(setName);
        return ResponseEntity.ok(assessment);
    }

    @PostMapping
    public ResponseEntity<?> createAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        AssessmentDTO createdAssessment = assessmentService.createAssessment(assessmentDTO);
        return ResponseEntity.ok(createdAssessment);
    }

    @PutMapping("/{setId}/questions/{questionId}")
    public ResponseEntity<?> updateQuestionOptions(@PathVariable Long setId, @PathVariable Long questionId,
                                                   @RequestBody List<AnswerDTO> answerDTOs) {
        assessmentService.updateQuestionOptions(setId, questionId, answerDTOs);
        return ResponseEntity.ok("Question options updated successfully");
    }

    @DeleteMapping("/{setId}/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long setId, @PathVariable Long questionId) {
        assessmentService.deleteQuestion(setId, questionId);
        return ResponseEntity.ok("Question deleted successfully");
    }
}
