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
        // TODO get skills, get cv/cv path from JSON message (for testing, later from Application form)
        // TODO get application id, get job opening id from process

        // get Process Variables from Message
        String first_name = delegateExecution.getVariable("first_name").toString();
        String last_name = delegateExecution.getVariable("last_name").toString();
        String email = delegateExecution.getVariable("email").toString();
        String sex = delegateExecution.getVariable("sex").toString();
        String birth_date_string = delegateExecution.getVariable("birth_date").toString();
        String title = delegateExecution.getVariable("title").toString();
        String address = delegateExecution.getVariable("address").toString();

        // format birth_date
        java.util.Date birth_date = (java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(birth_date_string);
        String birth_date_string_converted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(birth_date);

        // create JSON Process Variable containing Candidate data
        String candidate_master_data = "{\"first_name\" : \"" + first_name + "\","
                                            + "\"last_name\" : \"" + last_name + "\","
                                            + "\"birth_date\" : \"" + birth_date_string_converted + "\","
                                            + "\"sex\" : \"" + sex + "\","
                                            + "\"email\" :  \"" + email + "\","
                                            + "\"title\" :  \"" + title + "\","
                                            + "\"address\" :  \"" + address + "\""
                                            + "}";

        JsonValue jsonValue = SpinValues.jsonValue(candidate_master_data).create();
        delegateExecution.setVariable("new_candidate", jsonValue);

        // Find existing Candidates Entries in DB (first_name, last_name, birth_date)
        String count_query = "SELECT COUNT(*) as resultcount FROM Candidate " +
                                "WHERE first_name = \'" + first_name + "\' " +
                                "AND last_name = \'" + last_name + "\' " +
                                "AND birth_date = \'" + birth_date_string + "\'";

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        Statement query = con.createStatement();
        ResultSet rs = query.executeQuery( count_query);
        rs.next();
        int count = rs.getInt("resultcount");

        if (count == 0){
            delegateExecution.setVariable("CandidateAlreadyExists", false);
        }
        else if(count == 1){
            String candidate_id_query = "SELECT  candidate_id FROM Candidate " +
                                            "WHERE first_name = \'" + first_name + "\' " +
                                            "AND last_name = \'" + last_name + "\' " +
                                            "AND birth_date = \'" + birth_date_string + "\'";
            rs = query.executeQuery(candidate_id_query);
            rs.next();
            Integer candidate_id = rs.getInt(1);
            delegateExecution.setVariable("CandidateAlreadyExists", true);
            delegateExecution.setVariable("candidate_id", candidate_id);
        }
        else{
            // TODO throw error: conflict, multiple entries found
        }
    }
}
