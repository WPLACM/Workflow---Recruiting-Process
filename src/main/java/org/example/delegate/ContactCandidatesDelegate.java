package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import java.util.ArrayList;

public class ContactCandidatesDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        for(int i = 0; i < ((ArrayList<String>) execution.getVariable("candidates_email")).size(); i++){
            String message = ((ArrayList<String>) execution.getVariable("ListOfMails")).get(i);
            //System.out.println("Message: "+message);

             //Send to:
            String mailAddress = ((ArrayList<String>) execution.getVariable("candidates_email")).get(i);
            //System.out.println("Sent to: "+mailAddress);

            //TODO actually send message to mailAddress
        }

    }
}
