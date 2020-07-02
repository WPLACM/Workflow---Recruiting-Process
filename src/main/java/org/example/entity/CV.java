package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity

public class CV {

    @Id
    @GeneratedValue
    private int cv_id;
    private int rating;
    private int cv_rating;

    @OneToMany(targetEntity = Application.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cv_ap_fk", referencedColumnName = "application_id")
    private List<Application> applicationList;
}

