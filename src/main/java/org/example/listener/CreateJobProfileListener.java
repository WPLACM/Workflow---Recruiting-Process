package org.example.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CreateJobProfileListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String jobProfile = (String) execution.getVariable("jobProfile");

        String instert_query = "INSERT INTO Job_Profile (job_Profile) VALUES " +
                "(\'" + jobProfile + "\')";

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        PreparedStatement statement = con.prepareStatement(instert_query);
        Integer index = statement.executeUpdate();
    }
}
