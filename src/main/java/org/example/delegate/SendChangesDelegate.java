package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.JobOpeningInformation;
import org.springframework.web.client.RestTemplate;

public class SendChangesDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        RestTemplate template = new RestTemplate();
        /*
        JobOpeningInformation jobInfo = new JobOpeningInformation();
        jobInfo.setWBIG_processInstanceID((String) execution.getVariable("wbig_processInstanceId"));
        jobInfo.setOpening_name((String) execution.getVariable("openingName"));
        jobInfo.setOpen_spots_initial((String) execution.getVariable("openingName"));
        jobInfo.setOpen_spots_remaining((String) execution.getVariable("openingName"));
        jobInfo.setSalary((String) execution.getVariable("openingName"));
        jobInfo.setJob_title((String) execution.getVariable("openingName"));
        jobInfo.setJob_description((String) execution.getVariable("openingName"));
        jobInfo.setRequired_qualifications((String) execution.getVariable("openingName"));
        jobInfo.setAdditional_information((String) execution.getVariable("openingName"));
        jobInfo.setDeadline((String) execution.getVariable("openingName"));
        jobInfo.setRewardPerAcceptance((String) execution.getVariable("openingName"));
        jobInfo.setJob_location((String) execution.getVariable("openingName"));
        jobInfo.setWorking_hours((String) execution.getVariable("openingName"));
    */
    }
}
