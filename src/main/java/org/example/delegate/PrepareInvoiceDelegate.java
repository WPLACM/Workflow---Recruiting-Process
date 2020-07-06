package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

//TODO testing

public class PrepareInvoiceDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer number_of_acceptances = (Integer) delegateExecution.getVariable("number_of_acceptances");
        Integer payment_info = (Integer) delegateExecution.getVariable("payment_info");
        //int number_acceptances = Integer.parseInt(delegateExecution.getVariable("number_of_acceptances").toString());
        //int paymentInfo = Integer.parseInt(delegateExecution.getVariable("payment_information_acceptances").toString());

        Integer net = number_of_acceptances*payment_info;
        delegateExecution.setVariable("net", net);
        Double tax = net*0.19;
        delegateExecution.setVariable("tax", tax);
        Double gross = net+tax;
        delegateExecution.setVariable("gross", gross);
    }

}
