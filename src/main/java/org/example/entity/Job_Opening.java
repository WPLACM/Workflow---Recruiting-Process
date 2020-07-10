package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data   //generate Getter and Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Job_Opening {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer jobOpeningId;
    private Date openingDate;

    @OneToMany(mappedBy = "job_opening")
    private List<Application> applicationList ;

    @ManyToOne(targetEntity = Job_Profile.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_JobProfileId", referencedColumnName = "jobProfileId")
    private Job_Profile job_profile;

    //This has to be deleted    :Maxi 9.07 11:46
    @ManyToOne(targetEntity = Job_Opening_Information.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "jo_joi_fk", referencedColumnName = "job_opening_information_id")
    private Job_Opening_Information job_opening_information;
}
