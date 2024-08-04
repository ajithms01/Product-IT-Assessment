package com.ust.AssessmentMicro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answer")
public class Answer {

    @Id

    private Long id; // Primary key for Option entity
    private String optionText;
    private String suggestion;

    // Getters and setters
}
