package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.example.database.Opening;
import org.example.model.ProcessVariables;

// creates bean using spring boot
@Component
public class Test_Message implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(Test_Message.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("Send opening job information started");
        RestTemplate template = new RestTemplate();

        // requires data-object class. Set variables in object
        Opening payload = new Opening();
        payload.setWBIG_processInstanceID(delegateExecution.getProcessInstanceId());
        payload.setOpen_spots_initial((Integer) delegateExecution.getVariable("open_spots_initial"));
        payload.setSalary((Double) delegateExecution.getVariable("salary"));
        payload.setJob_title((String) delegateExecution.getVariable("job_title"));
        payload.setOpening_name((String) delegateExecution.getVariable("opening_name"));
        payload.setJob_description((String) delegateExecution.getVariable("job_description"));
        payload.setRequired_qualifications((String) delegateExecution.getVariable("required_qualifications"));
        payload.setAdditional_information((String) delegateExecution.getVariable("additional_information"));
        payload.setDeadline(new SimpleDateFormat("dd/MM/yyyy").parse((String)delegateExecution.getVariable("deadline")));
        payload.setRewardPerAcceptance((Double) delegateExecution.getVariable("payment_information_acceptances"));
        payload.setJob_location((String) delegateExecution.getVariable("job_location"));
        payload.setWorking_hours((Integer) delegateExecution.getVariable("working_hours"));
        payload.add();

        LOGGER.info("Opening data in data base");

        delegateExecution.setVariable("open_spots_remaining", delegateExecution.getVariable("open_spots"));


        // sends data-object to url (String class specification needed)
        String wplacm_processInstanceId = template.postForObject("http://localhost:8080/weplacm/weplacm", payload, String.class);
        //the following variable is necessary to link the response (see controller) !!!
        delegateExecution.setVariable("wplacm_processInstanceId", wplacm_processInstanceId);

        LOGGER.info("Send opening job information ended");
    }
}
