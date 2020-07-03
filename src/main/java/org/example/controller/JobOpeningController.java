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
                                .putValue("openingId", jobInfo.getInformation().getOpening_ID())
                                .putValue("openSpots" , jobInfo.getInformation().getOpen_spots())
                                .putValue("salary" , jobInfo.getInformation().getSalary())
                                .putValue("jobTitle" , jobInfo.getInformation().getJob_title())
                                .putValue("openingName" , jobInfo.getInformation().getOpening_name())
                                .putValue("jobDescription" , jobInfo.getInformation().getJob_description())
                                .putValue("requiredQualifications" , jobInfo.getInformation().getRequired_qualifications())
                                .putValue("additionalInformation" , jobInfo.getInformation().getAdditional_information())
                                .putValue("deadline" , jobInfo.getInformation().getDeadline())
                                .putValue("paymentInformationAcceptances" , jobInfo.getInformation().getPayment_information_acceptances())
                                .putValue("jobLocation" , jobInfo.getInformation().getJob_location())
                                .putValue("workingHours" , jobInfo.getInformation().getWorking_hours()));

        return processInstance.getId();
    }
}
