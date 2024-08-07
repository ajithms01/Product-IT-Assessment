package com.ust.AssessmentMicro.Service;

import com.ust.AssessmentMicro.Entity.Assessment;
import com.ust.AssessmentMicro.Entity.Question;
import com.ust.AssessmentMicro.Repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    // Method to get all assessments
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    // Method to get an assessment by its set name
    public Optional<Assessment> getAssessmentBySetName(String setName) {
        Optional<Assessment> assessment = assessmentRepository.findBySetName(setName);
        if(assessment.isEmpty()){
            throw new IllegalArgumentException("Assessment Not found");
        }
        return assessment;
    }

    // Method to create a new assessment
    public Assessment createAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    // Method to update a specific question within an assessment
    public Optional<Assessment> updateQuestion(String setName, String questionId, Question questionDetails) {
        Assessment assessment = assessmentRepository.findBySetName(setName)
                .orElseThrow(() -> new NoSuchElementException("Assessment not found with set name: " + setName));

        Question question = assessment.getQuestions().stream()
                .filter(q -> q.getQuestionId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Question not found with ID: " + questionId));

        question.setQuestionName(questionDetails.getQuestionName());
        question.setOptions(questionDetails.getOptions());

        assessmentRepository.save(assessment);
        return Optional.of(assessment);
    }

    // Method to delete a specific question within an assessment
    public boolean deleteQuestion(String setName, String questionId) {
        Assessment assessment = assessmentRepository.findBySetName(setName)
                .orElseThrow(() -> new NoSuchElementException("Assessment not found with set name: " + setName));

        boolean removed = assessment.getQuestions().removeIf(question -> question.getQuestionId().equals(questionId));
        if (!removed) {
            throw new NoSuchElementException("Question not found with ID: " + questionId);
        }

        assessmentRepository.save(assessment);
        return true;
    }
}
