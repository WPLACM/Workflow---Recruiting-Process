package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.JobOpeningInformation;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class _wbigSendOpeningDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        RestTemplate template = new RestTemplate();

        JobOpeningInformation payload = new JobOpeningInformation();

        payload.setWbig_process_ID(execution.getProcessInstanceId());
        payload.setOpen_spots_initial(10);
        payload.setOpen_spots_remaining(8);
        payload.setSalary((long) 45000);
        payload.setJob_title("Junior Developer");
        payload.setOpening_name("Developer at WBIG");
        payload.setJob_description("Entry level developer who will spend his entire week on StackOverflow");
        payload.setRequired_qualifications("Java, Spring, sql");
        payload.setAdditional_information("Needs to love coding");
        payload.setDeadline("2020-10-01");
        payload.setReward_per_acceptance((long) 55);
        payload.setJob_location("Cologne");
        payload.setWorking_hours((long) 40);
        //add to database

        execution.setVariable("open_spots_remaining", execution.getVariable("open_spots"));
        String wbig_processInstanceId = template.postForObject("http://localhost:8080/jobOpening/start/" + execution.getProcessInstanceId(), payload, String.class);
        execution.setVariable("wbig_processInstanceId", wbig_processInstanceId); //needed here?

        //System.out.println("WBIG Prozess ID: "+ execution.getProcessInstanceId());
    }
}
