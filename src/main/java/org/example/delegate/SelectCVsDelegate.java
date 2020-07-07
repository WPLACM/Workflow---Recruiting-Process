package org.example.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.entity.Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SelectCVsDelegate implements JavaDelegate {
        List<Application> allCvs = new ArrayList<Application>();
      // List<Applicaton_Candidate_REsponse> response = new ArrayList<Applicaton_Candidate_REsponse>();


        @Override
        public void execute(DelegateExecution delegateExecution) throws Exception {
            Integer openingId = (Integer) delegateExecution.getVariable("openingId");
            //String min_rating_val = "SELECT min_config from CONFIG";
            String applications_query = "SELECT  application_id, cv_rating, backgroundrating  FROM Application " +
                    "WHERE jo_ap_fk = \'" + openingId + "\' AND rating > 55 ORDER BY rating desc limit 10";

            //SELECT  application_id, cv_rating, backgroundrating  FROM Application WHERE jo_ap_fk = \'" + openingId + "\' ";
            //String applications_query= "SELECT  application_id, cv_rating, backgroundrating,RATING  FROM Application  ORDER BY rating desc";
            Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery( applications_query);

            while(rs.next()) {
                Integer application_id = rs.getInt("application_id");
                Integer cv_rating = rs.getInt("cv_rating");
                Integer bg_rating = rs.getInt("backgroundrating");

                Application cv = new Application();
                cv.setApplication_id(application_id);
                cv.setCv_rating(cv_rating);
                cv.setBackgroundrating(bg_rating);

               // Candidate cand = new  DTO concept
                //Applicaton_Candidate_REsponse acp = new Applicaton_Candidate_REsponse();
                //acp.application = cv;
                //acp.candidate = cand;

                //response.add(acp);

                allCvs.add(cv);
            }

            ObjectMapper obj = new ObjectMapper();
            String cvJson = obj.writeValueAsString(allCvs);
            System.out.println("cvJson: "+cvJson);
            delegateExecution.setVariable("allCvJson",cvJson);
        }
    }
