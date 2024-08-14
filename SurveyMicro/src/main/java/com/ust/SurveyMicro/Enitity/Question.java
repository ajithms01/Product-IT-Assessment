package com.ust.SurveyMicro.Enitity;

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
    private String questionName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> options;


}
