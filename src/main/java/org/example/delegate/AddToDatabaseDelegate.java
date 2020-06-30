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


        // get data
        JsonValue candidate = delegateExecution.getVariableTyped("new_candidate");

        String s_birth_date = candidate.getValue().prop("birth_date").stringValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.util.Date parsed = sdf.parse(s_birth_date);
        java.sql.Date birth_date = new java.sql.Date(parsed.getTime());

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        //Statement query = con.createStatement();
        String candidate_insert =
                "INSERT INTO Candidate ( first_name, last_name, birth_date, sex, email) VALUES (?,?,?,?,?)";
        PreparedStatement statement = con.prepareStatement(candidate_insert, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, candidate.getValue().prop("first_name").stringValue() );
        statement.setString(2, candidate.getValue().prop("last_name").stringValue() );
        statement.setDate(3,  birth_date );
        statement.setString(4, candidate.getValue().prop("sex").stringValue() );
        statement.setString(5, candidate.getValue().prop("email").stringValue() );
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if(rs.next()) {
            long key = rs.getLong(1);
            System.out.println("inserted row: " + key);
        }
        // return canidate id, refer to specific job opening


    }
}
