package com.ust.AssessmentMicro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long questionId;
    private String questionName;// Unique identifier for the question (global across sets)
    private Long setId; // Identifier for the set this question belongs to



    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> options; // List of options for this question

    // Constructors, getters, and setters
}
