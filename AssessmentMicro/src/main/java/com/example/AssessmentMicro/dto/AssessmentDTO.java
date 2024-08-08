package com.example.AssessmentMicro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentDTO {

    private Long setId;
    private String setName;
    private String createdBy;
    private String domain;
    private List<QuestionDTO> questions;
}
