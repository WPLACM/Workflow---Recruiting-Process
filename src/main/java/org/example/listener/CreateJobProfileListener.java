package org.example.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import java.sql.*;

public class CreateJobProfileListener implements ExecutionListener  {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String jobProfile = (String) execution.getVariable("jobProfile");
        Integer jobOpeningInformationId = (Integer) execution.getVariable("jobOpeningInformationId");

        //todo: insert job_opening_id into FK key column
        String insert_query =
                "INSERT INTO Job_Profile ( job_Profile, FK_Job_Opening_Information_Id) VALUES (?,?)";
                //"(\'" + jobProfile + "\')";
        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        PreparedStatement statement = con.prepareStatement(insert_query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, (String) execution.getVariable("jobProfile"));
        statement.setInt(2, (Integer) execution.getVariable("jobOpeningInformationId"));
        Integer index = statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int id = generatedKeys.getInt(1);
            execution.setVariable("jobProfileId", id);
        }

    }
}
