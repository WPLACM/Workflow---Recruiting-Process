package org.example.WBIG_dummy;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.example.model.CancelOrder;

public class Test_Message_CancelledO implements JavaDelegate{
    private final Logger LOGGER = Logger.getLogger(Test_Message_noC.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        RestTemplate template = new RestTemplate();

        System.out.println("Test");

        // TESTING
        //delegateExecution.setVariable("wplacm_processInstanceId", delegateExecution.getProcessInstanceId());
        // TESTING
        //String wplacm_processInstanceId = (String) delegateExecution.getVariable("wplacm_processInstanceId");
        CancelOrder cancelledOrder = new CancelOrder(delegateExecution.getProcessInstanceId());


        String wplacm_processInstanceId = template.postForObject("http://localhost:8080/Billing/CancelOrder/" + delegateExecution.getProcessInstanceId(), cancelledOrder, String.class);
        //delegateExecution.setVariable("wplacm_processInstanceId", wplacm_processInstanceId);
    }
}

