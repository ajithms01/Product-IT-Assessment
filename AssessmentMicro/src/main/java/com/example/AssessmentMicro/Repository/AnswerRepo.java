package com.example.AssessmentMicro.Repository;

import com.example.AssessmentMicro.Entity.Answer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepo extends JpaRepository<Answer,Long> {
    @Transactional
    void deleteByQuestionId(Long questionId);
}
