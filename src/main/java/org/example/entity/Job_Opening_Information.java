package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.DateTime;
import java.util.Date;
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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String job_opening_information_id;

    private String WBIG_process_ID;
    private String opening_name;
    private Integer open_spots; //initial
    private Integer open_spots_remaining; //new from wbig
    private Double salary;
    private String job_title;
    private String job_description;
    private String required_qualifications;
    private String additional_information;
    private Date deadline;
    private Double reward_per_acceptance; //new from wbig
    private String job_location;
    private Integer working_hours; //double before


    @OneToMany(mappedBy = "job_opening_information")
    private List<Job_Profile> job_profileList;
}
