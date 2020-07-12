package org.example.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.entity.Application;
import org.example.entity.ApplicationMessage;
import org.example.entity.ApplicationMessageList;

import java.awt.*;
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
            /*
            String applications_query = "SELECT  application_id, cv_rating, backgroundrating  FROM Application " +
                    "WHERE jo_ap_fk = \'" + openingId + "\' AND rating > 55 ORDER BY rating desc limit 10";
            */

            String applications_query = "SELECT  A.application_id, A.rating, C.candidate_id, C.first_name, C.last_name, C.email, C.sex, C.title, C.address " +
                    "FROM Application A " +
                    "INNER JOIN Candidate C ON (C.candidate_id = A.ca_ap_fk) " +
                    "WHERE FK_job_Opening_Id = \'" + openingId + "\' AND rating > 80 ORDER BY rating desc limit 10";


            Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery( applications_query);

            String wbig_id = (String) delegateExecution.getVariable("wbig_processInstanceId");
            String wplacm_id = delegateExecution.getProcessInstanceId();

            while(rs.next()) {
                ApplicationMessage cv = new ApplicationMessage();

                cv.setApplicant_id(rs.getInt("application_id"));
                cv.setApplicant_name(rs.getString("first_name") + " " +
                        rs.getString("last_name"));
                cv.setWplacm_rating(rs.getInt("rating"));
                cv.setApplicant_email(rs.getString("email"));
                cv.setWbig_process_instance_id(wbig_id);
                cv.setWplacm_process_instance_id(wplacm_id);
                cv.setApplicant_gender(rs.getString("sex"));
                cv.setApplicant_title(rs.getString("title"));
                cv.setApplicant_address(rs.getString("address"));

                try {
                    selectedCVs.getApplicationList().add(cv);
                } catch (NullPointerException n) {
                    System.out.println("nullpointer");
                    System.out.println(n.getMessage());
                }
            }
            delegateExecution.setVariable("finalSelection", selectedCVs);
        }
    }
