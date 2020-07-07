package org.example.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.entity.Application;
import org.example.entity.ApplicationMessage;
import org.example.entity.ApplicationMessageList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SelectCVsDelegate implements JavaDelegate {
        //List<Application> allCvs = new ArrayList<Application>();
        ApplicationMessageList selectedCVs = new ApplicationMessageList();
      // List<Applicaton_Candidate_REsponse> response = new ArrayList<Applicaton_Candidate_REsponse>();


        @Override
        public void execute(DelegateExecution delegateExecution) throws Exception {
            Integer openingId = (Integer) delegateExecution.getVariable("openingId");
            //String min_rating_val = "SELECT min_config from CONFIG";
            String applications_query = "SELECT  application_id, cv_rating, backgroundrating  FROM Application " +
                    "WHERE jo_ap_fk = \'" + openingId + "\' AND rating > 55 ORDER BY rating desc limit 10";


            Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery( applications_query);

            Integer i = 0;

            while(rs.next()) {
                Integer application_id = rs.getInt("application_id");
                Integer cv_rating = rs.getInt("cv_rating");
                Integer bg_rating = rs.getInt("backgroundrating");

                ApplicationMessage cv = new ApplicationMessage();
                cv.setApplicant_id(1+i);
                i+=1;
                cv.setApplicant_email("asdfg");
                cv.setWplacm_rating(77);
                cv.setApplicant_name("Tesst");

               // Candidate cand = new  DTO concept
                //Applicaton_Candidate_REsponse acp = new Applicaton_Candidate_REsponse();
                //acp.application = cv;
                //acp.candidate = cand;

                //response.add(acp);
                try {
                    // selectedCVs.getApplicationList().add(cv);

                    List<ApplicationMessage> list = selectedCVs.getApplicationList();
                    list.add(cv);
                    selectedCVs.setApplicationList(list);

                    System.out.println("after add cv");
                } catch (NullPointerException n) {
                    System.out.println("nullpointer");
                    System.out.println(n.getMessage());
                }

            }
            /*
            ObjectMapper obj = new ObjectMapper();
            String cvJson = obj.writeValueAsString(allCvs);
            System.out.println("cvJson: "+cvJson);
            delegateExecution.setVariable("allCvJson",cvJson);

             */

            delegateExecution.setVariable("finalSelection", selectedCVs);
        }
    }
