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
    private String job_opening_information_id; //Integer before
    private String wbig_processInstanceId; //Integer before
    private String opening_name; //new from wbig
    private Integer open_spots; //initial
    private Integer open_spots_remaining; //new from wbig
    private Long salary; //String before
    private String job_title;
    private String job_description;
    private String required_qualifications;
    private String additional_information;
    private String deadline; //String before
    private Long reward_per_acceptance; //new from wbig
    private String job_location;
    private Long working_hours; //double before


    @OneToMany(targetEntity = Job_Profile.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "joi_jp_fk", referencedColumnName = "job_opening_information_id")
    private List<Job_Profile> job_profileList;
}
