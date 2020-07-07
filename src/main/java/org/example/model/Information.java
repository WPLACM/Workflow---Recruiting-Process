package org.example.model;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.h2.util.json.JSONValue;

@Getter
@Setter
public class Information {
    /*
    Attributes
     */
    //private Integer opening_ID;
    private String opening_name;
    private Integer open_spots_initial;
    private Integer open_spots_remaining;
    private Double salary;
    private String job_title;
    private String job_description;
    private String required_qualifications;
    private String additional_information;
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private String deadline;
    private double rewardPerAcceptance;
    private String job_location;
    private Integer working_hours;

    public Information(String opening_name, Integer open_spots_initial, Integer open_spots_remaining,
                       Double salary, String job_title, String job_description, String required_qualifications, String additional_information,
                       String deadline, double rewardPerAcceptance, String job_location, Integer working_hours){
        this.opening_name = opening_name;
        this.open_spots_initial = open_spots_initial;
        this.open_spots_remaining = open_spots_remaining;
        this.salary = salary;
        this.job_title = job_title;
        this.job_description = job_description;
        this.required_qualifications = required_qualifications;
        this.additional_information = additional_information;
        this.deadline = deadline;
        this.rewardPerAcceptance = rewardPerAcceptance;
        this.job_location = job_location;
        this.working_hours = working_hours;
    }

}
