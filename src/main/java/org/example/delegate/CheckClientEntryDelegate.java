package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import java.text.SimpleDateFormat;
import java.sql.*;

public class CheckClientEntryDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // TODO get candidate Data (Name,...) from Application form

        // Candidate: first_name, last_name, birth_date, sex
        String first_name, last_name, sex, email;
        first_name = last_name = sex = email = "test1";
        java.sql.Date birth_date = new java.sql.Date(0, 00, 01);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String birth_date_string = sdf.format(birth_date);

        String json = "{\"first_name\" : \"" + first_name + "\","
                + "\"last_name\" : \"" + last_name + "\","
                + "\"birth_date\" : \"" + birth_date_string + "\","
                + "\"sex\" : \"" + sex + "\","
                + "\"email\" :  \"" + email + "\""
                + "}";

        JsonValue jsonValue = SpinValues.jsonValue(json).create();
        delegateExecution.setVariable("new_candidate", jsonValue);

        String select_query = "SELECT COUNT(*) as resultcount FROM Candidate WHERE first_name = \'" + first_name +"\'";
        System.out.println(select_query);
        // query candidate db for candidate
        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        Statement query = con.createStatement();
        ResultSet rs = query.executeQuery( select_query);
        rs.next();
        int count = rs.getInt("resultcount");

        if (count == 0){
            delegateExecution.setVariable("CandidateAlreadyExists", false);
        }
        else if(count == 1){
            rs = query.executeQuery(
                    "SELECT candidate_id FROM Candidate WHERE first_name = \'" + first_name +"\'");
            rs.next();
            int candidate_id = rs.getInt(1);
            delegateExecution.setVariable("CandidateAlreadyExists", true);
            delegateExecution.setVariable("candidate_id", candidate_id);
        }
        else{
            // TODO throw error: conflict, multiple entries found
        }
    }
}
