package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data   //generate Getter and Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Candidate {
    @Id
    @GeneratedValue
    private int candidate_id;
    private String first_name;
    private String last_name;
    private Date birth_date;
    private String sex;
    private String email;
    private String skills;

    @OneToMany(targetEntity = Application.class, cascade = CascadeType.ALL)
    @JoinColumn(name ="ca_ap_fk", referencedColumnName = "candidate_id")
    private List<Application> applicationList;

}


