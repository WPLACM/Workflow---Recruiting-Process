package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.JobOpeningInformation;
import org.example.utility.wbigRestEndpoints;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendChangesDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        RestTemplate template = new RestTemplate();
        String wbig_processInstanceId = (String) execution.getVariable("wbig_processInstanceId");

        //create object and fill with variables from process context
        JobOpeningInformation jobInfo = new JobOpeningInformation();
        jobInfo.setWBIG_processInstanceID(wbig_processInstanceId);
        jobInfo.setOpening_name((String) execution.getVariable("openingName"));
        jobInfo.setOpen_spots_initial(Math.toIntExact((Long)execution.getVariable("openSpots")));
        jobInfo.setOpen_spots_remaining((Integer) execution.getVariable("openSpotsRemaining"));
        jobInfo.setSalary(Double.valueOf((String) execution.getVariable("salary")));
        jobInfo.setJob_title((String) execution.getVariable("jobTitle"));
        jobInfo.setJob_description((String) execution.getVariable("jobDescription"));
        jobInfo.setRequired_qualifications((String) execution.getVariable("requiredQualifications"));
        jobInfo.setAdditional_information((String) execution.getVariable("additionalInformation"));
        String rewardPerAcceptances = (String) execution.getVariable("paymentInformationAcceptances");
        jobInfo.setRewardPerAcceptance(Double.valueOf(rewardPerAcceptances));
        jobInfo.setJob_location((String) execution.getVariable("jobLocation"));
        jobInfo.setWorking_hours(Math.toIntExact((Long) execution.getVariable("workingHours")));
        jobInfo.setDeadline(new SimpleDateFormat("dd/MM/yyyy").parse((String)execution.getVariable("deadline")));

        //set endpoint and post object
        String EndpointUrl = new wbigRestEndpoints().getCurrent_URL();
        wbig_processInstanceId = template.postForObject(EndpointUrl + "wbig/jobopeningreply/" + wbig_processInstanceId, jobInfo, String.class);
    }
}
