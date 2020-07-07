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
        RestTemplate template = new RestTemplate();

        String wplacm_processInstanceId = (String) delegateExecution.getVariable("wplacm_processInstanceId");
        DebitAuthorization da = new DebitAuthorization(delegateExecution.getProcessInstanceId(), "DE00121545487");


        wplacm_processInstanceId = template.postForObject("http://localhost:8080/Billing/DA/" + wplacm_processInstanceId, da, String.class);
        //delegateExecution.setVariable("wplacm_processInstanceId", wplacm_processInstanceId);

        System.out.println("Debit Authorization successfully sent");
    }
}

