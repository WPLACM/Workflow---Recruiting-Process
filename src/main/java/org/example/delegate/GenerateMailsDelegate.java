package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.ArrayList;

public class GenerateMailsDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //access jobProfile information
        String jobProfile = (String) execution.getVariable("jobProfile");

        //access list of potential candidates and generate generic mails
        ArrayList<String> emails = new ArrayList<String>();
        for(int i = 0; i < ((ArrayList<String>) execution.getVariable("candidates_email")).size(); i++){
            String currentFirstName = ((ArrayList<String>) execution.getVariable("candidates_first_name")).get(i);
            String currentLastName = ((ArrayList<String>) execution.getVariable("candidates_last_name")).get(i);

            String mail ="Dear "+currentFirstName+" "+currentLastName+", " + System.lineSeparator() +
                    "we are pleased to announce to you that there is a new job offer that you might be interested in. "+ System.lineSeparator() +
                    "Please find all relevant information below."+System.lineSeparator()+ "Sincerely, Weplacm Headhunting. "+System.lineSeparator()+
                    jobProfile;

            emails.add(mail);
        }

        execution.setVariable("ListOfMails", emails);
    }
}
