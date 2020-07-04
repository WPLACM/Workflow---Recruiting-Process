package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class sortCVsDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

            Integer openingId = (Integer) delegateExecution.getVariable("openingId");
            String applications_query = "SELECT  application_id, cv_rating, backgroundrating  FROM Application " +
                    "WHERE jo_ap_fk = \'" + openingId + "\' ORDER BY rating desc";

            //SELECT  application_id, cv_rating, backgroundrating  FROM Application WHERE jo_ap_fk = \'" + openingId + "\' ";

            Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery( applications_query);

            while(rs.next()){
                Integer application_id  =rs.getInt("application_id");
                Integer cv_rating = rs.getInt("cv_rating");
                Integer bg_rating = rs.getInt("backgroundrating");

                System.out.println(application_id + cv_rating+bg_rating);
            }
    }
}