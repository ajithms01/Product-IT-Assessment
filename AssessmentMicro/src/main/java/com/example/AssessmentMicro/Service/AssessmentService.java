package com.example.AssessmentMicro.Service;

import com.example.AssessmentMicro.Entity.Answer;
import com.example.AssessmentMicro.Entity.Assessment;
import com.example.AssessmentMicro.Repository.AnswerRepo;
import com.example.AssessmentMicro.Repository.AssessmentRepository;
import com.example.AssessmentMicro.Repository.QuestionRepo;
import com.example.AssessmentMicro.Entity.Question;
import com.example.AssessmentMicro.dto.Answerdto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<String> getQuestionNamesBySetName(String setName) {
        Assessment assessment = assessmentRepository.findBySetName(setName)
                .orElseThrow(() -> new NoSuchElementException("Assessment not found with set name: " + setName));
        return assessment.getQuestions().stream()
                .map(Question::getQuestionName)
                .collect(Collectors.toList());
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
    public Optional<Assessment> updateQuestionOptions(Long setId, Long questionId, List<Answerdto> optionDTOs) {
        Assessment assessment = assessmentRepository.findById(setId)
                .orElseThrow(() -> new NoSuchElementException("Assessment not found with set Id: " + setId));

        Question question = assessment.getQuestions().stream()
                .filter(q -> q.getQuestionId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Question not found with ID: " + questionId));

        // Clear existing options
        question.getOptions().clear();

        // Convert OptionDTO to Answer and add to the list
        optionDTOs.forEach(dto -> {
            Answer option = new Answer();
            option.setOptionText(dto.getOptionText());
            option.setSuggestion(dto.getSuggestion());
            option.setQuestionId(questionId);
            question.getOptions().add(option);
            repo1.save(option);
        });

        // Save the updated question
        repo2.save(question);

        // Save the updated assessment
        assessmentRepository.save(assessment);

        return Optional.of(assessment);
    }

    // Method to delete a specific question within an assessment
    @Transactional
    public boolean deleteQuestion(Long setId,Long questionId) {
        Assessment assessment = assessmentRepository.findById(setId)
                .orElseThrow(() -> new NoSuchElementException("Assessment not found with set id: " + setId));

        // Find the question to be removed
        Question questionToRemove = assessment.getQuestions().stream()
                .filter(question -> question.getQuestionId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Question not found with ID: " + questionId));

        // Remove the question from the assessment
        assessment.getQuestions().remove(questionToRemove);

        // Save the assessment without the removed question
        assessmentRepository.save(assessment);

        // Delete the question from the database
        repo2.deleteById(questionId);

        return true;
    }
}
