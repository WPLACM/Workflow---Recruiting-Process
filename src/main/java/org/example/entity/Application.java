package org.example.entity;

import camundajar.impl.scala.Int;
import com.sun.istack.Nullable;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer application_id;
    private Integer rating;
    private Integer cv_rating;
    private Integer backgroundrating;
    private String cv_link;

    private DateTime acceptedDate;  //optional to-do: set when final selection complete
    private DateTime rejectedDate;  //optional to-do: set when final selection complete

    @ManyToOne(targetEntity = Job_Opening.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_jobOpeningId", referencedColumnName = "jobOpeningId")
    private Job_Opening job_opening;
}
