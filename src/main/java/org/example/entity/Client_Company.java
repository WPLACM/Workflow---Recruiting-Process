package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data   //generate Getter and Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Client_Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer company_id; //foreign key in Job_opening_Information Table
    private String name;
    private String description;
    @OneToMany(targetEntity = Job_Opening_Information.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_joi_fk", referencedColumnName = "company_id")
    private List<Job_Opening_Information> job_opening_informationList;
}
