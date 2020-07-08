package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.spin.plugin.variable.value.impl.JsonValueImpl;
import org.example.entity.Application;
import org.example.entity.ApplicationMessage;
import org.example.entity.ApplicationMessageList;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class SendFinalSelectionDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("send final selection");

        ObjectValue typedApplicationCollection =
                (ObjectValue) delegateExecution.getVariableTyped("finalSelection");

        ApplicationMessageList final_selection = (ApplicationMessageList) typedApplicationCollection.getValue();

        String wbig_processInstanceId = (String) delegateExecution.getVariable("wbig_processInstanceId");
        //TODO Create Application Messages in order to ensure the wplacm_processInstanceId is stored
        String wplacm_processInstanceId = (String) delegateExecution.getProcessInstanceId();
        RestTemplate template = new RestTemplate();
        try {
            wplacm_processInstanceId =
                    template.postForObject("http://localhost:8080/wbig/wbig_cvs/" + wplacm_processInstanceId + "/" + wbig_processInstanceId, final_selection, String.class);
        } catch (Exception e) {
            System.out.println("error post");
            System.out.println(e.getMessage());

        }


        /*
        String allCvJson = (String) delegateExecution.getVariable("allCvJson");
        //send json where where?
         */
    }


}
