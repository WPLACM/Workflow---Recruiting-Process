package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;

public class enhanceCVsWithRatingsDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // get all applications/cvs for this job opening
        Integer openingId = (Integer) delegateExecution.getVariable("openingId");
        String applications_query = "SELECT  application_id, cv_rating, backgroundrationg  FROM Application " +
                "WHERE jo_ap_fk = \'" + openingId + "\' ";

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        Statement query = con.createStatement();
        ResultSet rs = query.executeQuery( applications_query);

        // iterate all found applications
        while (rs.next()){
            Integer application_id  =rs.getInt("application_id");
            Integer cv_raring = rs.getInt("cv_rating");
            Integer bg_rating = rs.getInt("backgroundrating");

            // TODO calculate rating
            Integer rating = 100;

            // update db entry
            // Prepare Update Statement
            String s_update_application =
                    "UPDATE Application  SET" +
                            " rating = \'" + rating +
                            " WHERE application_id = " + application_id;

            PreparedStatement update_application = con.prepareStatement(s_update_application);
            int index = update_application.executeUpdate();
        }
    }
}