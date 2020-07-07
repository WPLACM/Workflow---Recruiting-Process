package org.example.WBIG_dummy;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.example.model.NumberOfCandidates;

// creates bean using spring boot
@Component
public class Test_Message_noC implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(Test_Message_noC.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        RestTemplate template = new RestTemplate();

        // requires data-object class. Set variables in object
        NumberOfCandidates payload = new NumberOfCandidates(delegateExecution.getProcessInstanceId(), 1,50.5);
        System.out.println("WBIG Prozess ID_NOC: "+ delegateExecution.getProcessInstanceId());

        // sends data-object to url (String class specification needed)
        String wbig_processInstanceId = template.postForObject("http://localhost:8080/Billing/start/" + delegateExecution.getProcessInstanceId(), payload, String.class);
        //the following variable is necessary to link the response (see controller) !!!
        delegateExecution.setVariable("wbig_processInstanceId", wbig_processInstanceId);
        System.out.println("After Delegate WBIG ProcessInstanceId: " + wbig_processInstanceId);

    }
}
