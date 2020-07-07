package org.example.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NumberOfCandidates {
    /*
    Attributes
     */
    private Integer wbig_process_ID;
    private Integer number_of_acceptances;
    private Integer payment_info;
    //private JSONValue information;
    /*
    private String opening_ID;
    private int open_spots;
    private int salary;
    private String job_title;
    private String opening_name;
    private String job_description;
    private String required_qualifications;
    private String additional_information;
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private String deadline;
    private int payment_information_acceptances;
    private String job_location;
    private int working_hours;
     */
}