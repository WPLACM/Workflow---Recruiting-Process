package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.entity.Application;

import java.util.List;

public class SendFinalSelectionDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // String allCvJson = delegateExecution.getVariable("allCvJson",cvJson);

        //send where?
    }


}
