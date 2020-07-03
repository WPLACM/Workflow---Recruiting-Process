package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int application_id;



    //TO-DO: specify additional attributes, sql foreign key and relation config
    private int cv_id;
    private int rating;
    private int cv_rating;
    private int backgroundrating;
    //private XXX link_to_cv; still to be defined
}
