package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

public class UpdateDatabseRecordDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        // get data
        JsonValue candidate = delegateExecution.getVariableTyped("new_candidate");

        // update candidate record
        //candidate.getValue()

        // refer to specific job opening

    }
}
