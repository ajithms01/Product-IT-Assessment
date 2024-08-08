package com.example.AssessmentMicro.Repository;

import com.example.AssessmentMicro.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepo extends JpaRepository<Answer,Long> {
}
