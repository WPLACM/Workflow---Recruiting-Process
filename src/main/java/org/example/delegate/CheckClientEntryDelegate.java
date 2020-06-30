package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.entity.Candidate;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CheckClientEntryDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // TODO get candidate Data (Name,...) from Application form
        // Candidate: first_name, last_name, birth_date, sex
        String first_name, last_name, sex, email;
        first_name = last_name = sex = email = "test";
        java.sql.Date birth_date = new java.sql.Date(1900, 01, 01);

        Candidate applying_candidate = new Candidate(first_name, last_name, birth_date, sex, email);

        // query candidate db for candidate
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jdbc:h2:./camunda-dbobjectdb");
        EntityManager entitymanager = emf.createEntityManager();
        Long count = (Long) entitymanager.createQuery( "Select e from Candidate e where e.first_name = 'test' " ).getSingleResult();

        if (count == 0){
            delegateExecution.setVariable("CandidateAlreadyExists", false);
            // TODO hand over candidate object/ candidate information to next task?
        }
        else if(count == 1){
            delegateExecution.setVariable("CandidateAlreadyExists", true);
            // TODO hand over candidate id/ candidate object to next task?
        }
        else{
            // TODO throw error: conflict, multiple entries found
        }
    }
}
