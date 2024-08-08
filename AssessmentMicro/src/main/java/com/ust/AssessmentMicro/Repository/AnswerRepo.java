package com.ust.AssessmentMicro.Repository;

import com.ust.AssessmentMicro.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepo extends JpaRepository<Answer,Long> {
}
