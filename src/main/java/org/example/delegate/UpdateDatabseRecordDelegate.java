package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

import java.sql.*;
import java.text.SimpleDateFormat;

public class UpdateDatabseRecordDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        // get data from candidate JSON process variable
        JsonValue candidate = delegateExecution.getVariableTyped("new_candidate");
        Integer candidate_id = (Integer) delegateExecution.getVariable("candidate_id");

        // Convert birthdate
        String s_birth_date = candidate.getValue().prop("birth_date").stringValue();
        java.util.Date util_birth_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(s_birth_date);
        java.sql.Date birth_date = new java.sql.Date(util_birth_date.getTime());

        // Prepare Update Statement
        String candidate_update =
                "UPDATE Candidate  SET " +
                        //"first_name = \'" + candidate.getValue().prop("first_name").stringValue() + "\', " +
                        //"last_name = \'" + candidate.getValue().prop("last_name").stringValue() + "\', " +
                        "sex = \'" + candidate.getValue().prop("sex").stringValue() + "\', " +
                        "email = \'" + candidate.getValue().prop("email").stringValue() + "\' " +
                        //"birth_date = \'" + birth_date + "\' " +
                        "WHERE candidate_id = " + candidate_id;

        System.out.println(candidate_update);

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        PreparedStatement statement = con.prepareStatement(candidate_update);
        int index = statement.executeUpdate();

        // Index = Updated PK id
        System.out.println(index);

        //Dummy set job opening id
        delegateExecution.setVariable("job_opening_id", 1);

        // insert new application to db
        String application_insert = "INSERT INTO Application ( CA_AP_FK, JO_AP_FK) VALUES (?,?)";
        PreparedStatement statement_application = con.prepareStatement(application_insert, Statement.RETURN_GENERATED_KEYS);
        statement_application.setInt(1, candidate_id);
        statement_application.setInt(2, (Integer) delegateExecution.getVariable("job_opening_id"));
        statement_application.executeUpdate();

        // set process variable required for next step
        delegateExecution.setVariable("applications_received", true);

    }
}
