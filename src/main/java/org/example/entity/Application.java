package org.example.entity;

import com.sun.istack.Nullable;
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
    private Integer application_id;
    private Integer rating;
    private Integer cv_rating;
    private Integer backgroundrating;

    public Integer getApplication_id() {
        return application_id;
    }

    public void setApplication_id(Integer application_id) {
        this.application_id = application_id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getCv_rating() {
        return cv_rating;
    }

    public void setCv_rating(Integer cv_rating) {
        this.cv_rating = cv_rating;
    }

    public Integer getBackgroundrating() {
        return backgroundrating;
    }

    public void setBackgroundrating(Integer backgroundrating) {
        this.backgroundrating = backgroundrating;
    }

    //private ??? link to cv file

    //TO-DO: specify additional attributes, sql foreign key and relation config
}
