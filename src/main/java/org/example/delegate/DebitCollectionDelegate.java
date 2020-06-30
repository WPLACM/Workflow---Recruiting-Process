package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DebitCollectionDelegate implements JavaDelegate {
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("debit", true);
}
}