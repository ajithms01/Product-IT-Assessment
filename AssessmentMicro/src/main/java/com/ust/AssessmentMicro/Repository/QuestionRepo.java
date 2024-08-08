package com.ust.AssessmentMicro.Repository;

import com.ust.AssessmentMicro.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question,Long> {
}
