package org.example.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class clarifyJOListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String jobTitle = (String) execution.getVariable("jobTitle");
        String openingName = (String) execution.getVariable("openingName");
        String jobDescription = (String) execution.getVariable("jobDescription");
        String jobLocation = (String) execution.getVariable("jobLocation");
        String requiredQualifications = (String) execution.getVariable("requiredQualifications");
        String strSalary = (String) execution.getVariable("salary");
        Double salary = Double.parseDouble(strSalary);
        Long hoursL = (Long) execution.getVariable("workingHours");
        Integer workingHours = hoursL.intValue();
        Long openSpotsL = (Long) execution.getVariable("openSpots");
        Integer openSpots = openSpotsL.intValue();
        String additionalInformation = (String) execution.getVariable("additionalInformation");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse((String)execution.getVariable("deadline"));
        DateFormat test = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String deadline = test.format(date);

        String strReward = (String) execution.getVariable("paymentInformationAcceptances");
        Double rewardPerAcceptance = Double.parseDouble(strReward);
        String wbigProcessId = (String) execution.getVariable("wbig_processInstanceId");

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
