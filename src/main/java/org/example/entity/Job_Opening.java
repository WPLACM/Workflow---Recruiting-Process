package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
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
    private Timestamp deadline;

    @OneToMany(targetEntity = Application.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "jo_ap_fk", referencedColumnName = "jobOpeningId")
    private List<Application> applicationList ;

    @ManyToOne(targetEntity = Job_Profile.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "jo_jp_fk", referencedColumnName = "jobProfileId")
    private Job_Profile job_profile;

    @ManyToOne(targetEntity = Job_Opening_Information.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "jo_joi_fk", referencedColumnName = "job_opening_information_id")
    private Job_Opening_Information job_opening_information;
}
