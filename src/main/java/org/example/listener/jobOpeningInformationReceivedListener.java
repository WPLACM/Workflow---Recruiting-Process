package org.example.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

public class jobOpeningInformationReceivedListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
            String job_opening_insert =
                    "INSERT INTO Job_Opening_Information ( WBIG_process_ID, opening_name, open_spots, open_spots_remaining," +
                            " salary, job_title, job_description, required_qualifications, additional_information, deadline," +
                            " reward_per_acceptance, job_location, working_hours) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = con.prepareStatement(job_opening_insert, Statement.RETURN_GENERATED_KEYS);

            // Set values for insert
            String s = (String) execution.getVariable("salary");
            double salary = Double.parseDouble(s);
            String p = (String) execution.getVariable("paymentInformationAcceptances");
            double payment = Double.parseDouble(p);
            statement.setString(1, (String) execution.getVariable("wbig_processInstanceId"));
            statement.setString(2, (String) execution.getVariable("openingName"));
            statement.setInt(3, (Integer) execution.getVariable("openSpots"));
            statement.setInt(4, (Integer) execution.getVariable("openSpotsRemaining"));
            statement.setDouble(5, salary);
            statement.setString(6, (String) execution.getVariable("jobTitle"));
            statement.setString(7, (String) execution.getVariable("jobDescription"));
            statement.setString(8, (String) execution.getVariable("requiredQualifications"));
            statement.setString(9, (String) execution.getVariable("additionalInformation"));
            String datetest = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format((Date) execution.getVariable("deadlineDateFormat"));
            statement.setString(10, datetest);
            statement.setDouble(11, payment);
            statement.setString(12, (String) execution.getVariable("jobLocation"));
            statement.setInt(13, (Integer) execution.getVariable("workingHours"));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                 execution.setVariable("jobOpeningInformationId", Math.toIntExact(id));
            }
        }
        catch (java.sql.SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
        }
    }
}
