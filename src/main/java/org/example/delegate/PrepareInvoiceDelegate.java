package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class PrepareInvoiceDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer number_of_acceptances = (Integer) delegateExecution.getVariable("number_of_acceptances");
        Integer payment_info = (Integer) delegateExecution.getVariable("payment_info");

        Integer net = number_of_acceptances*payment_info;
        delegateExecution.setVariable("net", net);
        Double tax = net*0.19;
        delegateExecution.setVariable("tax", tax);
        Double gross = net+tax;
        delegateExecution.setVariable("gross", gross);
    }

}
