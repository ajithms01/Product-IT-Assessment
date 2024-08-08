package com.ust.AssessmentMicro.Service;

import com.ust.AssessmentMicro.Entity.Answer;
import com.ust.AssessmentMicro.Entity.Assessment;
import com.ust.AssessmentMicro.Entity.Question;
import com.ust.AssessmentMicro.Repository.AnswerRepo;
import com.ust.AssessmentMicro.Repository.AssessmentRepository;
import com.ust.AssessmentMicro.Repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AnswerRepo repo1;

    @Autowired
    private QuestionRepo repo2;

    // Method to get all assessments
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    public Optional<Assessment> getAssessmentBySetName(String setName) {
        return assessmentRepository.findBySetName(setName);
    }
    // Method to get an assessment by its set name
    public Optional<Assessment> getAssessmentBySetId(Long setId) {
        Optional<Assessment> assessment = assessmentRepository.findById(setId);
        if(assessment.isEmpty()){
            return null;
        }
        return assessment;
    }

    // Method to create a new assessment
    public Assessment createAssessment(Assessment assessment) {

        assessment.setCreatedTimestamp(LocalDateTime.now());
        assessment.setUpdatedTimestamp(LocalDateTime.now());
        assessment.setUpdatedBy("admin"); // Assuming default value for updatedBy

        Assessment savedAssessment = assessmentRepository.save(assessment);

        for (Question question : assessment.getQuestions()) {
            question.setSetId(savedAssessment.getSetId()); // Set the assessment setId in each question

            Question savedQuestion = repo2.save(question);

            for (Answer option : question.getOptions()) {
                option.setQuestionId(savedQuestion.getQuestionId()); // Set the questionId in each option

                repo1.save(option);
            }
        }

        return savedAssessment;
    }

    // Method to update a specific question within an assessment
    public Optional<Assessment> updateQuestion(Long setId, Long questionId, Question questionDetails) {
        Assessment assessment = assessmentRepository.findById(setId)
                .orElseThrow(() -> new NoSuchElementException("Assessment not found with set Id: " + setId));

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
    public boolean deleteQuestion(Long setId,Long questionId) {
        Assessment assessment = assessmentRepository.findById(setId)
                .orElseThrow(() -> new NoSuchElementException("Assessment not found with set id: " + setId));

        boolean removed = assessment.getQuestions().removeIf(question -> question.getQuestionId().equals(questionId));
        if (!removed) {
            throw new NoSuchElementException("Question not found with ID: " + questionId);
        }

        assessmentRepository.save(assessment);
        return true;
    }
}
