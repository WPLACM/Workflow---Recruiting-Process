package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;
import java.util.ArrayList;

public class CreateCandidateListDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            createTestEntries(); //include for tests
        } catch (SQLException e) {
            System.out.println("test entries already existing");
        }
        //get relevant information from process context
        String location = (String) execution.getVariable("jobLocation");
        String qualifications = (String) execution.getVariable("requiredQualifications");

        //filter single qualifications
        //qualifications = qualifications.replaceAll("\\s+","");
        qualifications = qualifications.trim();
        String[] qual = qualifications.split(",");

        //create SQL query
        String candidats_query = "SELECT first_name, last_name, email FROM Candidate ";
        if(qual.length == 0){
            candidats_query += "WHERE location_city = \'"+ location +"\'";
        }
        else{
            candidats_query += "WHERE (";
            for(int i = 0; i < qual.length; i++){
                candidats_query += "skills LIKE \'%"+qual[i]+"%\'";
                if(!(i == (qual.length -1))){
                    candidats_query += " OR ";
                }
            }
            candidats_query += ") OR skills = \'"+qualifications+"\'";
            candidats_query += " AND location_city = \'"+ location +"\'";
        }
        System.out.println("Create Candidate List Query: "+candidats_query); //TODO

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
                "VALUES (12, 'Donald', 'Duck', 'wplacmrecruiting@gmail.com', 'Camunda', 'Muenster')";
        String insert_query2 = "INSERT INTO Candidate (candidate_id, first_name, last_name, email, skills, location_city) "+
                "VALUES (2, 'Max', 'Mustermann', 'wplacmrecruiting@gmail.com', 'Java, Spring', 'Muenster')";
        String insert_query3 = "INSERT INTO Candidate (candidate_id, first_name, last_name, email, skills, location_city) "+
                "VALUES (3, 'Lisa', 'Cain', 'wplacmrecruiting@gmail.com', 'M.Sc. Computer Science', 'Muenster')";
        String insert_query4 = "INSERT INTO Candidate (candidate_id, first_name, last_name, email, skills, location_city) "+
                "VALUES (4, 'John', 'Doe', 'wplacmrecruiting@gmail.com', 'Java, M.Sc. Computer Science', 'Muenster')";
        String insert_query5 = "INSERT INTO Candidate (candidate_id, first_name, last_name, email, skills, location_city) "+
                "VALUES (5, 'Peter', 'Parker', 'wplacmrecruiting@gmail.com', 'Python', 'Cologne')";
        String insert_query6 = "INSERT INTO Candidate (candidate_id, first_name, last_name, email, skills, location_city) "+
                "VALUES (6, 'Eva', 'Scott', 'wplacmrecruiting@gmail.com', 'Python, M.Sc. Computer Science', 'Berlin')";
        String insert_query7 = "INSERT INTO Candidate (candidate_id, first_name, last_name, email, skills, location_city) "+
                "VALUES (7, 'Anna', 'Mustermann', 'wplacmrecruiting@gmail.com', 'Java', 'Muenster')";

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        Statement query = con.createStatement();
        query.executeUpdate(insert_query1);
        query.executeUpdate(insert_query2);
        query.executeUpdate(insert_query3);
        query.executeUpdate(insert_query4);
        query.executeUpdate(insert_query5);
        query.executeUpdate(insert_query6);
        query.executeUpdate(insert_query7);
        query.close();
        con.close();
    }

}
