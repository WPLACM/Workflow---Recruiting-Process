package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.JobOpeningInformation;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@Component
public class _wbigSendOpeningDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        RestTemplate template = new RestTemplate();

        JobOpeningInformation payload = new JobOpeningInformation();

        payload.setWbig_process_ID(execution.getProcessInstanceId());
        payload.setOpen_spots_initial((Integer) execution.getVariable("open_spots_initial"));
        payload.setSalary((Double) execution.getVariable("salary"));
        payload.setJob_title((String) execution.getVariable("job_title"));
        payload.setOpening_name((String) execution.getVariable("opening_name"));
        payload.setJob_description((String) execution.getVariable("job_description"));
        payload.setRequired_qualifications((String) execution.getVariable("required_qualifications"));
        payload.setAdditional_information((String) execution.getVariable("additional_information"));
        payload.setDeadline(new SimpleDateFormat("dd/MM/yyyy").parse((String)execution.getVariable("deadline")));
        payload.setRewardPerAcceptance((Double) execution.getVariable("payment_information_acceptances"));
        payload.setJob_location((String) execution.getVariable("job_location"));
        payload.setWorking_hours((Integer) execution.getVariable("working_hours"));
        //add to database

        execution.setVariable("open_spots_remaining", execution.getVariable("open_spots"));
        String wplacm_processInstanceId = template.postForObject("http://localhost:8080/weplacm/weplacm", payload, String.class);
        execution.setVariable("wplacm_processInstanceId", wplacm_processInstanceId); //needed here?
    }
}
