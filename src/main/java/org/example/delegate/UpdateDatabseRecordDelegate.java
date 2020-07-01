package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

import java.sql.*;
import java.text.SimpleDateFormat;

public class UpdateDatabseRecordDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // get data
        JsonValue candidate = delegateExecution.getVariableTyped("new_candidate");
        Integer candidate_id = (Integer) delegateExecution.getVariable("candidate_id");

        String s_birth_date = candidate.getValue().prop("birth_date").stringValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.util.Date parsed = sdf.parse(s_birth_date);
        java.sql.Date birth_date = new java.sql.Date(parsed.getTime());

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        String candidate_update =
                "UPDATE Candidate SET email = " + candidate.getValue().prop("email").stringValue() +
                        " WHERE candidate_id = " + candidate_id;

        PreparedStatement statement = con.prepareStatement(candidate_update);
        statement.executeUpdate();

        ResultSet rs = statement.getResultSet();
        if(rs.next()){
            System.out.println("update row: " + candidate_id);
        }

        // return canidate id, refer to specific job opening


        // get data

        // update candidate record
        //candidate.getValue()

        // refer to specific job opening

    }        // get data
}
