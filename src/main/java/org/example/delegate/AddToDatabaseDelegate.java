package org.example.delegate;

import camundajar.impl.scala.Int;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AddToDatabaseDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        // get data from candidate JSON process variable
        JsonValue candidate = delegateExecution.getVariableTyped("new_candidate");

        // Convert birthdate
        String s_birth_date = candidate.getValue().prop("birth_date").stringValue();
        java.util.Date util_birth_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(s_birth_date);
        java.sql.Date birth_date = new java.sql.Date(util_birth_date.getTime());

        // Prepare Insert Statement
        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        String candidate_insert =
                "INSERT INTO Candidate ( first_name, last_name, birth_date, sex, email) VALUES (?,?,?,?,?)";
        PreparedStatement statement = con.prepareStatement(candidate_insert, Statement.RETURN_GENERATED_KEYS);

        // Set values for insert
        statement.setString(1, candidate.getValue().prop("first_name").stringValue() );
        statement.setString(2, candidate.getValue().prop("last_name").stringValue() );
        statement.setDate(3,  birth_date );
        statement.setString(4, candidate.getValue().prop("sex").stringValue() );
        statement.setString(5, candidate.getValue().prop("email").stringValue() );
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if(rs.next()) {
            // set candidate_id process variable
            long candidate_id = rs.getLong(1);
            System.out.println("inserted candidate: " + candidate_id);
            delegateExecution.setVariable("candidate_id", candidate_id);

            //Dummy set job opening id
            delegateExecution.setVariable("job_opening_id", 1);

            // insert new application to db
            String application_insert = "INSERT INTO Application ( CA_AP_FK, JO_AP_FK) VALUES (?,?)";
            PreparedStatement statement_application = con.prepareStatement(application_insert, Statement.RETURN_GENERATED_KEYS);
            statement_application.setInt(1, Integer.parseInt(Long.toString(candidate_id)));
            statement_application.setInt(2, (Integer) delegateExecution.getVariable("job_opening_id"));
            statement_application.executeUpdate();

        }

    }
}
