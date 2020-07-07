package org.example.entity;

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
    private Integer job_opening_id;
    private Date opening_date;
    private Timestamp deadline;

    @OneToMany(targetEntity = Application.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "jo_ap_fk", referencedColumnName = "job_opening_id")
    private List<Application> applicationList ;

    @ManyToOne(targetEntity = Job_Opening_Information.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "jo_joi_fk", referencedColumnName = "job_opening_information_id")
    private List<Job_Opening_Information> job_opening_informationList;
}
