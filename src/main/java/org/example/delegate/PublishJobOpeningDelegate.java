package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;
import java.text.SimpleDateFormat;

public class PublishJobOpeningDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        // TODO fill opening date and deadline from process variable
        // convert dates
        java.util.Date util_opening_date = (java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse("2020-07-04");
        String s_opening_date =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(util_opening_date);
        util_opening_date = (java.util.Date) new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(s_opening_date);
        java.sql.Date opening_date = new java.sql.Date(util_opening_date.getTime());

        java.util.Date util_deadline = (java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse("2020-07-09");
        String s_deadline =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(util_deadline);
        util_deadline = (java.util.Date) new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(s_deadline);
        java.sql.Timestamp deadline = new java.sql.Timestamp(util_deadline.getTime());

        // Prepare Insert Statement
        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        String job_opening_insert =
                "INSERT INTO JOB_OPENING ( opening_date, deadline, jo_joi_fk) VALUES (?,?,?)";
        PreparedStatement statement = con.prepareStatement(job_opening_insert, Statement.RETURN_GENERATED_KEYS);

        // Set values for insert
        statement.setDate(1, opening_date);
        statement.setTimestamp(2, deadline);
        statement.setInt(3, (Integer) execution.getVariable("opening_information_id"));
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if(rs.next()) {
            // set openingId process variable
            Integer job_opening_id =  rs.getInt(1);
            execution.setVariable("openingId", job_opening_id);
        }

    }
}
