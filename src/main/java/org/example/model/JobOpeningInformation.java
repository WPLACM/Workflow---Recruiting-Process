package org.example.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

import org.camunda.spin.plugin.variable.value.JsonValue;
import org.h2.util.json.JSONValue;
@Getter
@Setter
public class JobOpeningInformation {
    /*
    Attributes
     */
    private String wbig_process_ID;
    //private String time_stamp;
    private String opening_name;
    private Integer open_spots_initial;
    private Integer open_spots_remaining;
    private Integer salary; //double
    private String job_title;
    private String job_description;
    private String required_qualifications;
    private String additional_information;
    private Date deadline;
    private Integer reward_per_acceptance; //double
    private String job_location;
    private Integer working_hours;

    public JobOpeningInformation(String wbig_process_ID, String opening_name, Integer open_spots_initial,
                                 Integer open_spots_remaining, Integer salary, String job_title, String job_description,
                                 String required_qualifications, String additional_information, Date deadline,
                                 Integer reward_per_acceptance, String job_location, Integer working_hours){

        this.wbig_process_ID = wbig_process_ID;
        //this.time_stamp = time_stamp;
        this.opening_name = opening_name;
        this.open_spots_initial = open_spots_initial;
        this.open_spots_remaining = open_spots_remaining;
        this.salary = salary;
        this.job_title = job_title;
        this.job_description = job_description;
        this.required_qualifications = required_qualifications;
        this.additional_information = additional_information;
        this.deadline = deadline;
        this.reward_per_acceptance = reward_per_acceptance;
        this.job_location = job_location;
        this.working_hours = working_hours;
    }

    public JobOpeningInformation(){

    }
}