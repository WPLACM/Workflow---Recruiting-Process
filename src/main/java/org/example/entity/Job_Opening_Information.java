package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Job_Opening_Information {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer index;


    // todo maybe change time_stamp and deadline to timestamp type, (additional efforts required!)
    private Integer job_opening_information_id;
    private Integer WBIG_process_ID;
    private String time_stamp;
    private Integer open_spots;
    private String salary;
    private String job_title;
    private String job_description;
    private String required_qualifications ;
    private String additional_information;
    private String job_location;
    private double working_hours;
    private String deadline;

    @OneToMany(targetEntity = Job_Profile.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "joi_jp_fk", referencedColumnName = "job_opening_information_id")
    private List<Job_Profile> job_profileList;
}
