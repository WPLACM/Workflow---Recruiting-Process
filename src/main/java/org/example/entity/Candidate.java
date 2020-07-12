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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer candidate_id;
    private String first_name;
    private String last_name;
    private Date birth_date;
    private String sex;
    private String email;
    private String skills;
    private String location_country;
    private String location_city;
    private String title;
    private String address;

    @OneToMany(targetEntity = Application.class, cascade = CascadeType.ALL)
    @JoinColumn(name ="ca_ap_fk", referencedColumnName = "candidate_id")
    private List<Application> applicationList;

    public Candidate(String first_name, String last_name, Date birth_date, String sex, String email){
        this.first_name = first_name;
        this.last_name  = last_name;
        this.birth_date = birth_date;
        this.sex        = sex;
        this.email      = email;
    }
    public Candidate(String first_name, String last_name, Date birth_date, String sex, String email, String title, String address){
        this.first_name = first_name;
        this.last_name  = last_name;
        this.birth_date = birth_date;
        this.sex        = sex;
        this.email      = email;
        this.title      = title;
        this.address    = address;
    }
}


