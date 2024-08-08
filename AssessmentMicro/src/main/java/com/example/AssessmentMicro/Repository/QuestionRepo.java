package com.example.AssessmentMicro.Repository;

import com.example.AssessmentMicro.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question,Long> {
}
