package org.example.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import java.sql.*;

public class PublishJobOpeningEndListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        int id = (Integer) delegateExecution.getVariable("jobProfileId");

        String count_query = "SELECT jobOpeningId FROM Job_Opening " +
                "WHERE FK_JobProfileId = " + Integer.toString(id);

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        Statement query = con.createStatement();
        ResultSet rs = query.executeQuery( count_query);
        if(rs.next()) {
            delegateExecution.setVariable("openingId", rs.getInt("jobOpeningId"));
        }
    }
}
