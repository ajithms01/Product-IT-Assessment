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
public class Assessment {
    @Id
    private String setId;
    private String setName;
    private String createdBy;
    private String domain;
    private String status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

    // Getters and setters
}
