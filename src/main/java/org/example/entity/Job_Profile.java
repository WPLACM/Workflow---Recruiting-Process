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
public class Job_Profile {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer job_profile_id;
    private String company_name;
    private Integer open_spots;
    private String salary;
    private String job_title;
    private String job_description;
    private String required_qualifications;
    private String additional_information;
    private String job_location;
    private double working_hours;
    private DateTime deadline;
    private boolean it_job;

    //One Job Profile can have multiple job openings. Examplary case: Job openings to one job profile are posted on
    // different websites and have different deadlines which need to be maintained.
    @OneToMany(targetEntity = Job_Opening.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "jp_jo_fk", referencedColumnName = "job_profile_id")
    private List<Job_Opening> job_openingList;

}
