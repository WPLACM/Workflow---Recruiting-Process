package org.example.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.example.model.JobOpeningInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.*;

/*
REST Endpoint to enable starting the process via REST
 */

@RestController
@RequestMapping("/jobOpening")
public class JobOpeningController {

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping(path = "/start", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String startProcess(@RequestBody JobOpeningInformation jobInfo){
        ProcessInstance processInstance = runtimeService
                //.startProcessInstanceByKey("wplacm_id",
                .startProcessInstanceByKey("sid-9E969114-7F80-4315-A6D5-4D25DC5B40F1",
                        Variables.createVariables()
                                .putValue("WBIG_process_ID", jobInfo.getWbig_process_ID())
                                .putValue("time_stamp", jobInfo.getTime_stamp())
                                .putValue("opening_information_id", jobInfo.getOpening_name())
                                .putValue("openSpots" , jobInfo.getOpen_spots_initial())
                                .putValue("salary" , jobInfo.getOpen_spots_remaining())
                                .putValue("jobTitle" , jobInfo.getSalary())
                                .putValue("openingName" , jobInfo.getJob_title())
                                .putValue("jobDescription" , jobInfo.getJob_description())
                                .putValue("requiredQualifications" , jobInfo.getRequired_qualifications())
                                .putValue("additionalInformation" , jobInfo.getAdditional_information())
                                .putValue("deadline" , jobInfo.getDeadline())
                                .putValue("paymentInformationAcceptances" , jobInfo.getRewardPerAcceptance())
                                .putValue("jobLocation" , jobInfo.getJob_location())
                                // convert working hours process variable to string (no Double values possible in camunda forms)
                                .putValue("workingHours" , Double.toString(jobInfo.getWorking_hours())));

        // insert job opening information into db
        try {

            Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
            String job_opening_insert =
                    "INSERT INTO Job_Opening_Information ( job_opening_information_id, WBIG_process_ID, time_stamp, open_spots, salary, " +
                            "job_title, job_description, required_qualifications, additional_information, job_location, " +
                            "working_hours, deadline) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = con.prepareStatement(job_opening_insert, Statement.RETURN_GENERATED_KEYS);

            // Set values for insert
            //statement.setInt(1, jobInfo.getInformation().getOpening_ID());
            //statement.setInt(2, jobInfo.getWbig_process_ID());
            statement.setString(3, jobInfo.getTime_stamp());
            statement.setString(3, jobInfo.getOpening_name());
            //statement.setInt(4, jobInfo.getInformation().getOpen_spots());
            //statement.setInt(5, jobInfo.getSalary());
            statement.setString(6, jobInfo.getJob_title());
            statement.setString(7, jobInfo.getJob_description());
            statement.setString(8, jobInfo.getRequired_qualifications());
            statement.setString(9, jobInfo.getAdditional_information());
            //statement.setString(10, jobInfo.getDeadline());
            statement.setDouble(11, jobInfo.getRewardPerAcceptance());
            statement.setString(12, jobInfo.getJob_location());
            //statement.setString(12, jobInfo.getWorking_hours());
            statement.executeUpdate();
        } catch (java.sql.SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
        }

        return processInstance.getId();
    }
}
