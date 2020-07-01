package org.example.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.example.model.JobOpeningInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
REST Endpoint to enable starting the process via REST
 */

@RestController
@RequestMapping("/jobOpening")
public class JobOpeningController {

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping(path = "/start", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String startProcess(@RequestBody JobOpeningInformation jobInfo){
        ProcessInstance processInstance = runtimeService
                //.startProcessInstanceByKey("wplacm_id",
                .startProcessInstanceByKey("sid-9E969114-7F80-4315-A6D5-4D25DC5B40F1",
                        Variables.createVariables()
                                .putValue("openingId", jobInfo.getOpeningId())
                                .putValue("openSpots" , jobInfo.getOpenSpots())
                                .putValue("salary" , jobInfo.getSalary())
                                .putValue("jobTitle" , jobInfo.getJobTitle())
                                .putValue("openingName" , jobInfo.getOpeningName())
                                .putValue("jobDescription" , jobInfo.getJobDescription())
                                .putValue("requiredQualifications" , jobInfo.getRequiredQualifications())
                                .putValue("additionalInformation" , jobInfo.getAdditionalInformation())
                                .putValue("deadline" , jobInfo.getDeadline())
                                .putValue("paymentInformationAcceptances" , jobInfo.getPaymentInformationAcceptances())
                                .putValue("jobLocation" , jobInfo.getJobLocation())
                                .putValue("workingHours" , jobInfo.getWorkingHours()));

        return processInstance.getId();
    }
}
