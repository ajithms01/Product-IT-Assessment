package com.ust.SurveyMicro.Enitity;

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
public class SurveyDTO {
    @Id
    private String surveyId;
    private String domainName;
    private String companyName;
    private String email;
    private String setName;
    private String status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions; // Add this field

    // Getters and setters
}
