package com.ust.SurveyMicro.FeignClient;

import com.ust.SurveyMicro.Enitity.Assessment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "assessment-service", url = "http://localhost:8082")
public interface AssessmentClient {
    @GetMapping("/assessment/{setname}")
    ResponseEntity<Assessment> getAssessmentBySetName(@PathVariable("setname") String setname);
}
