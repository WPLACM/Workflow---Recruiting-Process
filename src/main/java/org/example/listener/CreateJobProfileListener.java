package org.example.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import java.sql.*;

public class CreateJobProfileListener implements ExecutionListener  {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String jobProfile = (String) execution.getVariable("jobProfile");

        String instert_query = "INSERT INTO Job_Profile (job_Profile) VALUES " +
                "(\'" + jobProfile + "\')";

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        PreparedStatement statement = con.prepareStatement(instert_query, Statement.RETURN_GENERATED_KEYS);
        Integer index = statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int id = generatedKeys.getInt(1);
            execution.setVariable("jobProfileId", id);
        }

    }
}
