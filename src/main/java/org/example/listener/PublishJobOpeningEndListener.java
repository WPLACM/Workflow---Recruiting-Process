package org.example.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class PublishJobOpeningEndListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        // TODO  set to openingId created in DB
        Integer test = 1;
        delegateExecution.setVariable("openingId", test );
    }
}
