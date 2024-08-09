package com.example.AssessmentMicro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private String questionName; // Removed questionId
    private List<AnswerDTO> options = List.of(); // Initialized as an empty list
}
