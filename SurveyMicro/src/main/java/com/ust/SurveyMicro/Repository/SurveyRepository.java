package com.ust.SurveyMicro.Repository;

import com.ust.SurveyMicro.Enitity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}

