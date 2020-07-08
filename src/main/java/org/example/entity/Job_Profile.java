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


    //One Job Profile can have multiple job openings. Examplary case: Job openings to one job profile are posted on
    // different websites and have different deadlines which need to be maintained.
    @OneToMany(mappedBy = "Job_Profile")
    private List<Job_Opening> job_openingList;

}
