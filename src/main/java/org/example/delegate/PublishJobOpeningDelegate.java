package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;

public class PublishJobOpeningDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        // Prepare Insert Statement
        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        String job_opening_insert =
                "INSERT INTO JOB_OPENING ( opening_date, deadline) VALUES (?,?)";
        PreparedStatement statement = con.prepareStatement(job_opening_insert, Statement.RETURN_GENERATED_KEYS);

        // Set values for insert
        // TODO fill oepning date and deadline from process variable
        statement.setString(1, "2020-07-03");
        statement.setString(2, "2020-07-03");
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if(rs.next()) {
            // set openingId process variable
            long job_opening_id = rs.getLong(1);
            execution.setVariable("openingId", job_opening_id);
        }

        //access jobProfile

        //create website

        //create entry on website


    }
}
