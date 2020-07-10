package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import org.springframework.web.client.RestTemplate;
import org.example.model.MoreCandidates;

public class _wbigSendMoreCandidatesDelegate implements JavaDelegate{
    private final Logger LOGGER = Logger.getLogger(_wbigSendCandidatesPlacedDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        RestTemplate template = new RestTemplate();

        String wplacm_processInstanceId = (String) delegateExecution.getVariable("wplacm_processInstanceId");

        MoreCandidates moreC = new MoreCandidates(delegateExecution.getProcessInstanceId());


        wplacm_processInstanceId = template.postForObject("http://localhost:8080/Billing/MoreCandidates/" + wplacm_processInstanceId, moreC, String.class);
        //delegateExecution.setVariable("wplacm_processInstanceId", wplacm_processInstanceId);
    }
}

