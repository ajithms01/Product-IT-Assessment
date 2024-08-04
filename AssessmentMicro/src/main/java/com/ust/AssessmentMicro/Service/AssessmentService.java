package com.ust.AssessmentMicro.Service;

import com.ust.AssessmentMicro.Entity.Assessment;
import com.ust.AssessmentMicro.Entity.Question;
import com.ust.AssessmentMicro.Repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Assessment getAssessmentBySetName(String setName) {
        return assessmentRepository.findBySetName(setName);
    }

    // Method to create a new assessment
    public Assessment createAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    // Method to update a specific question within an assessment
    public Assessment updateQuestion(String setId, String questionId, Question questionDetails) {
        Optional<Assessment> optionalAssessment = assessmentRepository.findById(setId);
        if (optionalAssessment.isPresent()) {
            Assessment assessment = optionalAssessment.get();
            for (Question question : assessment.getQuestions()) {
                if (question.getQuestionId().equals(questionId)) {
                    question.setQuestionName(questionDetails.getQuestionName());
                    question.setOptions(questionDetails.getOptions());
                    return assessmentRepository.save(assessment);
                }
            }
        }
        return null;
    }

    // Method to delete a specific question within an assessment
    public boolean deleteQuestion(String setId, String questionId) {
        Optional<Assessment> optionalAssessment = assessmentRepository.findById(setId);
        if (optionalAssessment.isPresent()) {
            Assessment assessment = optionalAssessment.get();
            boolean removed = assessment.getQuestions().removeIf(question -> question.getQuestionId().equals(questionId));
            if (removed) {
                assessmentRepository.save(assessment);
                return true;
            }
        }
        return false;
    }
}
