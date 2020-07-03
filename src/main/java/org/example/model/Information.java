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
    private Integer opening_ID;
    private Integer open_spots;
    private Integer salary;
    private String job_title;
    private String opening_name;
    private String job_description;
    private String required_qualifications;
    private String additional_information;
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private String deadline;
    private Integer payment_information_acceptances;
    private String job_location;
    private Integer working_hours;
}