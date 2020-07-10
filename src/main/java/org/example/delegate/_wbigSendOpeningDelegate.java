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

        payload.setWBIG_processInstanceID(execution.getProcessInstanceId());
        payload.setOpen_spots_initial(10);
        payload.setOpen_spots_remaining(8);
        payload.setSalary(45000.00);
        payload.setJob_title("Junior Developer");
        payload.setOpening_name("Developer at WBIG");
        payload.setJob_description("Entry level developer who will spend his entire week on StackOverflow");
        payload.setRequired_qualifications("Java, Spring, sql");
        payload.setAdditional_information("Needs to love coding");
        java.util.Date date = (java.util.Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/10/2020");
        payload.setDeadline(date);
        payload.setRewardPerAcceptance(55.50);
        payload.setJob_location("Cologne");
        payload.setWorking_hours(40);

        execution.setVariable("open_spots_remaining", execution.getVariable("open_spots"));
        String wbig_processInstanceId = template.postForObject("http://localhost:8080/jobOpening/start/" + execution.getProcessInstanceId(), payload, String.class);
    }
}
