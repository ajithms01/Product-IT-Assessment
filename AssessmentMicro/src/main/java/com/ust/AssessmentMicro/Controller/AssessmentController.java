package com.ust.AssessmentMicro.Controller;

import com.ust.AssessmentMicro.Entity.Assessment;
import com.ust.AssessmentMicro.Entity.Question;
import com.ust.AssessmentMicro.Service.AssessmentService;
import com.ust.AssessmentMicro.dto.Answerdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/assessment")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    // Endpoint to get all assessments
    @GetMapping
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        List<Assessment> assessments = assessmentService.getAllAssessments();
        return ResponseEntity.ok(assessments);
    }

<<<<<<< HEAD
    @GetMapping("/{setName}/questions")
    public ResponseEntity<?> getQuestionNamesBySetName(@PathVariable String setName) {
        try {
            List<String> questionNames = assessmentService.getQuestionNamesBySetName(setName);
            return ResponseEntity.ok(questionNames);
        } catch (Exception e) {
            return ResponseEntity.ok("Error retrieving question names: " + e.getMessage());
        }
    }

    // Endpoint to get an assessment by its set ID
    @GetMapping("/{setId}")
    public ResponseEntity<?> getAssessmentBySetId(@PathVariable Long setId) {
        try {
            Optional<Assessment> assessment = assessmentService.getAssessmentBySetId(setId);
            return assessment.isPresent() ? ResponseEntity.ok(assessment.get()) : ResponseEntity.ok("Assessment not found with set ID: " + setId);
        } catch (Exception e) {
            return ResponseEntity.ok("Error retrieving assessment: " + e.getMessage());
        }
=======
    // Endpoint to get an assessment by its set name
    @GetMapping("/{setId}")
    public ResponseEntity<Assessment> getAssessmentBySetId(@PathVariable Long setId) {

            Optional<Assessment> assessment = assessmentService.getAssessmentBySetId(setId);
        return assessment.isPresent() ? ResponseEntity.ok(assessment.get()) : ResponseEntity.notFound().build();

>>>>>>> 68541fccae57944c3b97a0518bf94a5e5ee55101
    }

    // Endpoint to create a new assessment
    @PostMapping
    public ResponseEntity<?> createAssessment(@RequestBody Assessment assessment) {
        try {
            Assessment createdAssessment = assessmentService.createAssessment(assessment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAssessment);
        } catch (Exception e) {
            return ResponseEntity.ok("Error creating assessment: " + e.getMessage());
        }
    }

    // Endpoint to update a specific question within an assessment
    @PutMapping("/{setId}/{questionId}")
<<<<<<< HEAD
    public ResponseEntity<?> updateQuestionOptions(@PathVariable Long setId, @PathVariable Long questionId,
                                                   @RequestBody List<Answerdto> optionDTOs) {
        try {
            Optional<Assessment> updatedAssessment = assessmentService.updateQuestionOptions(setId, questionId, optionDTOs);
            return updatedAssessment.isPresent() ? ResponseEntity.ok(updatedAssessment.get()) :
                    ResponseEntity.ok("Question not found with ID: " + questionId);
        } catch (Exception e) {
            return ResponseEntity.ok("Error updating question options: " + e.getMessage());
        }
=======
    public ResponseEntity<Assessment> updateQuestion(@PathVariable Long setId, @PathVariable Long questionId, @RequestBody Question questionDetails) {
        Optional<Assessment> updatedAssessment = assessmentService.updateQuestion(setId, questionId, questionDetails);
        return updatedAssessment.isPresent() ? ResponseEntity.ok(updatedAssessment.get()) : ResponseEntity.notFound().build();
>>>>>>> 68541fccae57944c3b97a0518bf94a5e5ee55101
    }

    // Endpoint to delete a specific question within an assessment
    @DeleteMapping("/{setId}/{questionId}")
<<<<<<< HEAD
    public ResponseEntity<String> deleteQuestion(@PathVariable Long setId, @PathVariable Long questionId) {
        try {
            boolean isDeleted = assessmentService.deleteQuestion(setId, questionId);
            return isDeleted ? ResponseEntity.ok("Question deleted successfully") : ResponseEntity.ok("Question not found with ID: " + questionId);
        } catch (Exception e) {
            return ResponseEntity.ok("Error deleting question: " + e.getMessage());
        }
=======
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long setId, @PathVariable Long questionId) {
        boolean isDeleted = assessmentService.deleteQuestion(setId, questionId);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
>>>>>>> 68541fccae57944c3b97a0518bf94a5e5ee55101
    }
}
