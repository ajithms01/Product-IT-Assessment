package com.ust.SurveyMicro.FeignClient;

import com.ust.SurveyMicro.Enitity.Assessment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "assessment-service", url = "http://localhost:8082/assessment")
public interface AssessmentClient {
    @GetMapping("/bysetname/{setname}")
    ResponseEntity<Assessment> getAssessmentBySetName(@PathVariable("setname") String setName);
}
