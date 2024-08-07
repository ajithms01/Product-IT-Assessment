package com.ust.AssessmentMicro.Controller;

import com.ust.AssessmentMicro.Entity.Assessment;
import com.ust.AssessmentMicro.Entity.Question;
import com.ust.AssessmentMicro.Service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // Endpoint to get an assessment by its set name
    @GetMapping("/{setName}")
    public ResponseEntity<Assessment> getAssessmentBySetName(@PathVariable String setName) {
        Optional<Assessment> assessment = assessmentService.getAssessmentBySetName(setName);
        return assessment.isPresent() ? ResponseEntity.ok(assessment.get()) : ResponseEntity.notFound().build();
    }

    // Endpoint to create a new assessment
    @PostMapping
    public ResponseEntity<Assessment> createAssessment(@RequestBody Assessment assessment) {
        Assessment createdAssessment = assessmentService.createAssessment(assessment);
        return ResponseEntity.status(201).body(createdAssessment);
    }

    // Endpoint to update a specific question within an assessment
    @PutMapping("/{setName}/{questionId}")
    public ResponseEntity<Assessment> updateQuestion(@PathVariable String setName, @PathVariable String questionId, @RequestBody Question questionDetails) {
        Optional<Assessment> updatedAssessment = assessmentService.updateQuestion(setName, questionId, questionDetails);
        return updatedAssessment.isPresent() ? ResponseEntity.ok(updatedAssessment.get()) : ResponseEntity.notFound().build();
    }

    // Endpoint to delete a specific question within an assessment
    @DeleteMapping("/{setName}/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String setName, @PathVariable String questionId) {
        boolean isDeleted = assessmentService.deleteQuestion(setName, questionId);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
