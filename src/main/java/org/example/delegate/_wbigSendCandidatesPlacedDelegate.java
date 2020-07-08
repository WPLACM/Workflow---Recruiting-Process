package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.example.model.NumberOfCandidates;

// CANDIDATES PLACED
@Component
public class _wbigSendCandidatesPlacedDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(_wbigSendCandidatesPlacedDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        RestTemplate template = new RestTemplate();

        // TESTING
        //delegateExecution.setVariable("wplacm_processInstanceId", delegateExecution.getProcessInstanceId());
        // TESTING
        //String wplacm_processInstanceId = (String) delegateExecution.getVariable("wplacm_processInstanceId");
        NumberOfCandidates payload = new NumberOfCandidates(delegateExecution.getProcessInstanceId(), 1,50.5);
        System.out.println("Send the amount of placed candidates");


        String wplacm_processInstanceId = template.postForObject("http://localhost:8080/Billing/CandidatesPlaced/" + delegateExecution.getProcessInstanceId(), payload, String.class);
        //delegateExecution.setVariable("wplacm_processInstanceId", wplacm_processInstanceId);



    }
}