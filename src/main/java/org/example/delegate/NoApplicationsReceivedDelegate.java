package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

public class NoApplicationsReceivedDelegate implements JavaDelegate {

    public void execute(DelegateExecution delegateExecution) throws Exception {
        JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name = client_company.getValue().prop("name").stringValue();

        String noapplicationsJSON = "{\"WPLACM_process_ID\" : \"ID\","
                + "\"WBIG_process_ID\" : \"ID\","
                + "\"timestamp\" : 12345\","
                + "\"message\" : \" Dear " + client_name + ", " +
                "sorry we were not able to find any suitable candidates for you. We are extremely sorry. " +
                "We hope we can continue doing services for you in the future for " +
                "any other job openings you will have. Thanks very much for your " +
                "understanding. Yours faithfully, WPLACM. \"}";

        delegateExecution.setVariable("NoApplicationsReceivedMessage", noapplicationsJSON);

    }
}
