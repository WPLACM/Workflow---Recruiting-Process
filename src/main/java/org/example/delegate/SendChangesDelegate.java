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
        jobInfo.setRewardPerAcceptance(Double.parseDouble((String) execution.getVariable("paymentInformationAcceptances"))); //TODO does not work
        jobInfo.setJob_location((String) execution.getVariable("jobLocation"));
        jobInfo.setWorking_hours(Math.toIntExact((Long) execution.getVariable("workingHours")));
        jobInfo.setDeadline(new SimpleDateFormat("dd/MM/yyyy").parse((String)execution.getVariable("deadline")));

        //TODO remove after testing
        System.out.println(jobInfo.getOpening_name());
        System.out.println(jobInfo.getOpen_spots_initial());
        System.out.println(jobInfo.getOpen_spots_remaining());
        System.out.println(jobInfo.getSalary());
        System.out.println(jobInfo.getJob_title());
        System.out.println(jobInfo.getJob_description());
        System.out.println(jobInfo.getRequired_qualifications());
        System.out.println(jobInfo.getAdditional_information());
        System.out.println("Reward in String: "+(String) execution.getVariable("paymentInformationAcceptances"));
        System.out.println("Reward in Double: "+Double.parseDouble((String) execution.getVariable("paymentInformationAcceptances")));
        System.out.println("Reward in Object: "+jobInfo.getRewardPerAcceptance());
        System.out.println(jobInfo.getJob_location());
        System.out.println(jobInfo.getWorking_hours());

        //set endpoint and post object
        String EndpointUrl = new wbigRestEndpoints().getCurrent_URL();
        wbig_processInstanceId = template.postForObject(EndpointUrl + "wbig/jobopeningreply/" + wbig_processInstanceId, jobInfo, String.class);
    }
}
