package org.example.model;

import camundajar.impl.scala.Int;
import lombok.Getter;
import lombok.Setter;
import org.camunda.feel.syntaxtree.In;

@Getter
@Setter
public class ApplicationCollectionElement {
    private Integer application_id;
    private Integer candidate_id;
    private String candidate_name;
    private String cv;
    private Integer rating;
    private Integer cv_rating;
    private Integer bg_rating;

    public ApplicationCollectionElement(Integer application_id, Integer candidate_id, String candidate_name, String cv) {
        this.application_id = application_id;
        this.candidate_id = candidate_id;
        this.candidate_name = candidate_name;
        this.cv = cv;
    }
}
