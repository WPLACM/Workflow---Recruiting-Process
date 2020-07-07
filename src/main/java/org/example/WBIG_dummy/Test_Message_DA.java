package org.example.WBIG_dummy;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.example.model.DebitAuthorization;

public class Test_Message_DA implements JavaDelegate{
    private final Logger LOGGER = Logger.getLogger(Test_Message_noC.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("Send debit authorization started");
        RestTemplate template = new RestTemplate();

        // requires data-object class. Set variables in object
        DebitAuthorization da = new DebitAuthorization(delegateExecution.getProcessInstanceId(), "DE00121545487");
        System.out.println("WBIG Prozess ID: "+ delegateExecution.getProcessInstanceId());
        //String wbig_processInstanceId = payload.getWBIG_processInstanceID();
        // sends data-object to url (String class specification needed)
        String wbig_processInstanceId = template.postForObject("http://localhost:8080/Billing/start/" + delegateExecution.getProcessInstanceId(), da, String.class);
        //the following variable is necessary to link the response (see controller) !!!
        delegateExecution.setVariable("wbig_processInstanceId", wbig_processInstanceId);

        LOGGER.info("Send debit authorization ended");
    }
}
}
