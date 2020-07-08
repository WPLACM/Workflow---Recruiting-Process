package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationMessage {
    private String wbig_process_instance_id;        // process instance id wbig
    private Integer applicant_id;                   // Candidate id
    private String applicant_name;
    private String applicant_address;               // tbd
    private String applicant_email;
    private String applicant_title;                 // tbd
    private String applicant_gender;
    private String wplacm_process_instance_id;      // wplacm process instance id
    private Integer wplacm_rating;                  // Application.rating
    //private Date interview_datetime;                // NULL
    private boolean is_active;                      // NULL
    //private Date acceptAquisition_deadline;         // NULL
}
