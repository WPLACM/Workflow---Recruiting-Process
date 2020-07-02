package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;
import java.util.ArrayList;

public class CreateCandidateListDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        createTestEntries(); //include for tests

        //get relevant information from process context
        String qualifications = (String) execution.getVariable("requiredQualifications");
        String location = (String) execution.getVariable("jobLocation");

        //create SQL query
        String candidats_query = "SELECT first_name, last_name, email FROM Candidate " +
               // "WHERE skills CONTAINS \'"+ qualifications + "\' " + /TODO filter by qualifications
                "WHERE location_city = \'"+ location + "\'";

        //create lists for relevant information of candidates
        ArrayList<String> candidates_first_name = new ArrayList<String>();
        ArrayList<String> candidates_last_name = new ArrayList<String>();
        ArrayList<String> candidates_email = new ArrayList<String>();

        //open connection and executing query
        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        Statement query = con.createStatement();
        ResultSet rs = query.executeQuery(candidats_query);

        //adding results into lists
        while(rs.next()){
            candidates_first_name.add(rs.getString("first_name"));
            candidates_last_name.add(rs.getString("last_name"));
            candidates_email.add(rs.getString("email"));
        }

        //closing connection
        rs.close();
        query.close();
        con.close();

        //Test results
        //for(int i = 0; i < candidates_first_name.size(); i++){
        //    System.out.println(candidates_first_name.get(i));
        //}

        //saving variables into process context
        execution.setVariable("candidates_first_name", candidates_first_name);
        execution.setVariable("candidates_last_name", candidates_last_name);
        execution.setVariable("candidates_email", candidates_email);

    }

    /*
    Creates Test Entries for the database that can be filtered
     */
    public void createTestEntries() throws SQLException {
        String insert_query1 = "INSERT INTO Candidate (candidate_id, first_name, last_name, email, skills, location_city) "+
                "VALUES (12, \'Donald\', \'Duck\', \'donald@web.de\', \'Java\', \'Cologne\')";
        String insert_query2 = "INSERT INTO Candidate (candidate_id, first_name, last_name, email, skills, location_city) "+
                "VALUES (2, 'Max', 'Mustermann', 'max@web.de', 'Spring', 'Cologne')";
        String insert_query3 = "INSERT INTO Candidate (candidate_id, first_name, last_name, email, skills, location_city) "+
                "VALUES (3, 'Dieter', 'Duck', 'dieter@web.de', 'Java', 'Muenster')";
        String insert_query4 = "INSERT INTO Candidate (candidate_id, first_name, last_name, email, skills, location_city) "+
                "VALUES (4, 'Tim', 'Tom', 'tim@web.de', 'Java, Spring', 'Cologne')";

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        Statement query = con.createStatement();
        query.executeUpdate(insert_query1);
        query.executeUpdate(insert_query2);
        query.executeUpdate(insert_query3);
        query.executeUpdate(insert_query4);
        query.close();
        con.close();
    }

}
