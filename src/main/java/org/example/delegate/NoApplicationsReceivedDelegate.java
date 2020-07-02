package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

public class NoApplicationsReceivedDelegate implements JavaDelegate{
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        // get name from Client Company JSON process variable
        JsonValue client_company = delegateExecution.getVariableTyped("name");

        String message = "Dear" + client_company.getValue().prop("name").stringValue() + " , sorry we were not " +
                "able to find any suitable candidates for you. We are extremely sorry. We hope we can continue " +
                "doing services for you in the future for any other job opening you will have."
                + System.lineSeparator() + "Thank you very much for you understanding." + System.lineSeparator() +
                "Sincerely, WPLACM Headhunting";
        System.out.println(message);
    }
}
