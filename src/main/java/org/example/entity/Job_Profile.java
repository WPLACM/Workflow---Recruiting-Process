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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobProfileId;
    private String jobProfile;



    @ManyToOne(targetEntity = Job_Opening_Information.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_JobOpeningInformationId", referencedColumnName = "index")
    private Job_Opening_Information job_opening_information;

    //One Job Profile can have multiple job openings. Examplary case: Job openings to one job profile are posted on
    // different websites and have different deadlines which need to be maintained.
    @OneToMany(mappedBy = "job_profile")
    private List<Job_Opening> job_openingList;

}
