package org.example.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class clarifyJOListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String jobTitle = (String) execution.getVariable("jobTitle");
        String openingName = (String) execution.getVariable("openingName");
        String jobDescription = (String) execution.getVariable("jobDescription");
        String jobLocation = (String) execution.getVariable("jobLocation");
        String requiredQualifications = (String) execution.getVariable("requiredQualifications");
        long salary = (long) execution.getVariable("salary");
        long workingHours = (long) execution.getVariable("workingHours");
        long openSpots = (long) execution.getVariable("openSpots");
        String additionalInformation = (String) execution.getVariable("additionalInformation");
        String deadline = (String) execution.getVariable("deadline");
        long rewardPerAcceptance = (long) execution.getVariable("rewardPerAcceptance");

        //TODO Update Database entries



    }
}
