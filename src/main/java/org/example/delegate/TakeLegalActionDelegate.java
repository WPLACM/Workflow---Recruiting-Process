package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

public class TakeLegalActionDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name=client_company.getValue().prop("name").stringValue();
        JsonValue job_opening = delegateExecution.getVariableTyped("new_job_opening");
        String opening_id=job_opening.getValue().prop("job_opening_id").stringValue();
        String message = "Dear Mr. Lawyer," + System.lineSeparator() +
                "our client " + client_name + " is not paying our invoice, even after sending him three bills, " +
                "for the job opening with the ID " +opening_id + "." + System.lineSeparator() +
                "Please take legal action." +System.lineSeparator() + "Sincerely, WPLACM Headhunting";

        System.out.println(message);

        //integrate mail plugin
    }
}
