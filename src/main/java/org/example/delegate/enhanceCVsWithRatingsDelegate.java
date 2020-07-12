package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;

import static java.lang.Math.round;

public class enhanceCVsWithRatingsDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // get all applications/cvs for this job opening
        Integer openingId = (Integer) delegateExecution.getVariable("openingId");
        String applications_query = "SELECT  application_id, cv_rating, backgroundrating  FROM Application " +
                "WHERE FK_job_Opening_Id = \'" + openingId + "\' ";

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        Statement query = con.createStatement();
        ResultSet rs = query.executeQuery( applications_query);

        // iterate all found applications
        while (rs.next()){
            Integer application_id  = rs.getInt("application_id");
            Integer cv_rating = rs.getInt("cv_rating");
            Integer bg_rating = rs.getInt("backgroundrating");

            Integer rating = 0;

            switch (bg_rating){
                case 1:
                    rating = cv_rating;
                    break;
                case 2:
                    rating = (Integer) Math.round((2*cv_rating/3));
                    break;
                case 3:
                    rating = 0;
                    break;
                default:
                    rating = cv_rating;
            }

            // update db entry
            // Prepare Update Statement
            String s_update_application =
                    "UPDATE Application  SET" +
                            " rating = " + rating +
                            " WHERE application_id = " + application_id;

            PreparedStatement update_application = con.prepareStatement(s_update_application);
            int index = update_application.executeUpdate();
        }
    }
}