package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.entity.Application;
import org.example.entity.ApplicationMessageList;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class SendFinalSelectionDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        ApplicationMessageList final_selection =
                (ApplicationMessageList) delegateExecution.getVariableTyped("finalSelection");

        RestTemplate template = new RestTemplate();
        String wplacm_processInstanceId =
                template.postForObject("http://localhost:8080/wbig/wbig_cvs", final_selection, String.class);

        /*
        String allCvJson = (String) delegateExecution.getVariable("allCvJson");
        //send json where where?
         */
    }


}
