package com.ust.AssessmentMicro.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private String questionId;
    private String questionName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> options;

    // Getters and setters
}
