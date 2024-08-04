package com.ust.AssessmentMicro.Repository;

import com.ust.AssessmentMicro.Entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, String> {
    // Custom query method to find an assessment by its set name
    Assessment findBySetName(String setName);
}
