package com.ust.SurveyMicro.Enitity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ust.SurveyMicro.Enitity.Question;


import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long surveyId;
    private String domainName;
    private String companyName;
    private String email;
    private String setName;
    private String status;
    private List<Long> questionIds;



    // Getters and setters
}
