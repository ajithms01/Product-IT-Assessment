package com.example.AssessmentMicro.Repository;

import com.example.AssessmentMicro.Entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    // Custom query method to find an assessment by its set name
    @Query("SELECT a FROM Assessment a WHERE LOWER(a.setName) = LOWER(:setName)")
    Optional<Assessment> findBySetName(@Param("setName") String setName);
}
