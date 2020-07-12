package org.example.controller;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.example.DemoDataGenerator;
import org.example.model.JobOpeningInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/*
REST Endpoint to enable starting the process via REST
 */

@RestController
@RequestMapping("/jobOpening")
public class JobOpeningController {
    private final java.util.logging.Logger LOGGER = Logger.getLogger(Controller.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping(path = "/start/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String startProcess(@RequestBody JobOpeningInformation jobInfo, @PathVariable("id") String wbig_processInstanceId){
        //create Users on first process instance
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        startCreateUsers(processEngine);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDeadline = dateFormat.format(jobInfo.getDeadline());
        String strSalary = jobInfo.getSalary().toString();
        String strReward = jobInfo.getRewardPerAcceptance().toString();

        ProcessInstance processInstance = runtimeService
            .startProcessInstanceByMessage("JobOpeningInformation",
                Variables.createVariables()
                    .putValue("wbig_processInstanceId", wbig_processInstanceId)
                    .putValue("openingName", jobInfo.getOpening_name())
                    .putValue("openSpots" , jobInfo.getOpen_spots_initial())
                    .putValue("openSpotsRemaining" , jobInfo.getOpen_spots_remaining()) //currently not in form
                    .putValue("salary" , strSalary)
                    .putValue("jobTitle" , jobInfo.getJob_title())
                    .putValue("jobDescription" , jobInfo.getJob_description())
                    .putValue("requiredQualifications" , jobInfo.getRequired_qualifications())
                    .putValue("additionalInformation" , jobInfo.getAdditional_information())
                    .putValue("deadline" , strDeadline)
                    .putValue("deadlineDateFormat", jobInfo.getDeadline())
                    .putValue("paymentInformationAcceptances" , strReward)
                    .putValue("jobLocation" , jobInfo.getJob_location())
                    .putValue("workingHours" , jobInfo.getWorking_hours()));

        // insert job opening information into db

        /*
        try {

            Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
            String job_opening_insert =
                    "INSERT INTO Job_Opening_Information ( WBIG_process_ID, opening_name, open_spots, open_spots_remaining," +
                            " salary, job_title, job_description, required_qualifications, additional_information, deadline," +
                            " reward_per_acceptance, job_location, working_hours) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = con.prepareStatement(job_opening_insert, Statement.RETURN_GENERATED_KEYS);

            // Set values for insert
            statement.setString(1, jobInfo.getWBIG_processInstanceID());
            statement.setString(2, jobInfo.getOpening_name());
            statement.setInt(3, jobInfo.getOpen_spots_initial());
            statement.setInt(4, jobInfo.getOpen_spots_remaining());
            statement.setDouble(5, jobInfo.getSalary());
            statement.setString(6, jobInfo.getJob_title());
            statement.setString(7, jobInfo.getJob_description());
            statement.setString(8, jobInfo.getRequired_qualifications());
            statement.setString(9, jobInfo.getAdditional_information());
            String dateTemp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(jobInfo.getDeadline());
            statement.setString(10, dateTemp);
            statement.setDouble(11, jobInfo.getRewardPerAcceptance());
            statement.setString(12, jobInfo.getJob_location());
            statement.setInt(13, jobInfo.getWorking_hours());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
            }
        } catch (java.sql.SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
        }

         */


        return processInstance.getId();
    }

    @PostDeploy
    public void startCreateUsers(ProcessEngine processEngine) {
        createUsers(processEngine);
    }
    private void createUsers(ProcessEngine processEngine) {

        // create demo users
        new DemoDataGenerator().createUsers(processEngine);
    }

}
