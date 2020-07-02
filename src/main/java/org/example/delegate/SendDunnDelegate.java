package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

public class SendDunnDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // number_of_dunns++;
        JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name=client_company.getValue().prop("name").stringValue();
        JsonValue job_opening = delegateExecution.getVariableTyped("new_job_opening");
        String opening_id=job_opening.getValue().prop("job_opening_id").stringValue();
        String message = "Dear Mr. Lawyer," + System.lineSeparator() +
                "we are sadly running into payment issues with " + client_name +
                " for the job opening with ID " + opening_id + "." + System.lineSeparator() +
                "Please initiate the dunning process." +System.lineSeparator() + "Sincerely, WPLACM Headhunting";

        System.out.println(message);

        // integrate mail plugin and include number_of_dunns as property of the job_opening
    }
}
