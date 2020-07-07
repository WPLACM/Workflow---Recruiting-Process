package org.example.WBIG_dummy;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.example.Database.NumberOfCandidates;
//import org.example.model.ProcessVariables;

// creates bean using spring boot
@Component
public class Test_Message implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(Test_Message.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("Send opening job information started");
        RestTemplate template = new RestTemplate();

        // requires data-object class. Set variables in object
        NumberOfCandidates payload = new NumberOfCandidates(delegateExecution.getProcessInstanceId(), 1,50);

        LOGGER.info("Opening data in data base");


        String wbig_processInstanceId = payload.getWBIG_processInstanceID();
        // sends data-object to url (String class specification needed)
        String wplacm_processInstanceId = template.postForObject("http://localhost:8080/Billing/" + wbig_processInstanceId, payload, String.class);
        //the following variable is necessary to link the response (see controller) !!!
        delegateExecution.setVariable("wplacm_processInstanceId", wplacm_processInstanceId);

        LOGGER.info("Send number of candidates ended");
    }
}
