package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

//TODO get paymentInformationAcceptances from job opening

public class PrepareInvoiceDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int number_acceptances = Integer.parseInt(delegateExecution.getVariable("number_of_acceptances").toString());
        int paymentInfo = Integer.parseInt(delegateExecution.getVariable("payment_information_acceptances").toString());

        int net=number_acceptances*paymentInfo;
        delegateExecution.setVariable("net", net);
        double tax=net*0.19;
        delegateExecution.setVariable("tax", tax);
        double gross=net+tax;
        delegateExecution.setVariable("gross", gross);
    }

}
