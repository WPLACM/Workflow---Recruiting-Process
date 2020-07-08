package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class PrepareInvoiceDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer number_of_acceptances = (Integer) delegateExecution.getVariable("number_of_acceptances");
        Double payment_info = (Double) delegateExecution.getVariable("payment_info");
        String date = (String) delegateExecution.getVariable("date");
        //Manual setting of invoice id
        String invoiceid = (String) delegateExecution.getVariable("openingid");
        invoiceid = invoiceid + " "+ date;
        Integer open = (Integer) delegateExecution.getVariable("openSpotsRemaining");

        try{
            if(number_of_acceptances>open){
                throw new Exception();
            }
            else{
                Double net = number_of_acceptances*payment_info;
                delegateExecution.setVariable("net", net);
                Double tax = net*0.19;
                delegateExecution.setVariable("tax", tax);
                Double gross = net+tax;
                delegateExecution.setVariable("gross", gross);

                delegateExecution.setVariable("invoiceid", invoiceid);
            }
        }
        catch (Exception e){
            System.out.print("The partner accepted more candidates than were predefined in the job opening.");
        }
    }

}
