package org.example.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import java.sql.*;

public class clarifyJOListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String jobTitle = (String) execution.getVariable("jobTitle");
        String openingName = (String) execution.getVariable("openingName");
        String jobDescription = (String) execution.getVariable("jobDescription");
        String jobLocation = (String) execution.getVariable("jobLocation");
        String requiredQualifications = (String) execution.getVariable("requiredQualifications");
        Long salaryL = (Long) execution.getVariable("salary");
        Integer salary = salaryL.intValue();
        Long hoursL = (Long) execution.getVariable("workingHours");
        Integer workingHours = hoursL.intValue();
        Long openSpotsL = (Long) execution.getVariable("openSpots");
        Integer openSpots = openSpotsL.intValue();
        String additionalInformation = (String) execution.getVariable("additionalInformation");
        String deadline = (String) execution.getVariable("deadline");
        Long rewardL = (Long) execution.getVariable("rewardPerAcceptance");
        Integer rewardPerAcceptance = rewardL.intValue();
        String wbigProcessId = (String) execution.getVariable("WBIG_process_ID");

        //create sql query
        String update_query = "UPDATE Job_Opening_Information SET " +
                "opening_name = \'" + openingName + "\', " +
                "open_spots = " + openSpots + ", " +
                "salary = " + salary + ", " +
                "job_title = \'" + jobTitle + "\', " +
                "job_description = \'" + jobDescription + "\', " +
                "required_qualifications = \'" + requiredQualifications + "\', " +
                "additional_information = \'" + additionalInformation + "\', " +
                "deadline = \'" + deadline + "\', " +
                "reward_per_acceptance = " + rewardPerAcceptance + ", " +
                "job_location = \'" + jobLocation + "\', " +
                "working_hours = " + workingHours + " " +
                "WHERE WBIG_process_ID = \'" + wbigProcessId + "\'";

        //open connection and execute statement
        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        PreparedStatement statement = con.prepareStatement(update_query);
        Integer index = statement.executeUpdate();

        //Close connection
        con.close();
    }
}
