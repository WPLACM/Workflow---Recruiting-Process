package org.example.entity;

import camundajar.impl.scala.Int;
import com.sun.istack.Nullable;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
