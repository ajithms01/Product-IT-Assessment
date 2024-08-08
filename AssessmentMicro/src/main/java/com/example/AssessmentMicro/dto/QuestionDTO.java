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
    private Long setId; // This will automatically be set when constructing the AssessmentDTO
    private List<AnswerDTO> options = List.of(); // Initialized as an empty list
}
