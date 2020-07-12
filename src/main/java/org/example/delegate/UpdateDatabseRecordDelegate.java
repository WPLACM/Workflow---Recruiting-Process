package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.camunda.spin.plugin.variable.value.impl.JsonValueImpl;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Collection;

import static org.camunda.spin.Spin.JSON;

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
                        "email = \'" + candidate.getValue().prop("email").stringValue() + "\', " +
                        "title = \'" + candidate.getValue().prop("title").stringValue() + "\', " +
                        "address = \'" + candidate.getValue().prop("address").stringValue() + "\' " +
                        //"birth_date = \'" + birth_date + "\' " +
                        "WHERE candidate_id = " + candidate_id;

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        PreparedStatement statement = con.prepareStatement(candidate_update);
        Integer index = statement.executeUpdate();

        // Index = Updated PK id
        System.out.println(index);

        // insert new application to db
        String application_insert = "INSERT INTO Application ( CA_AP_FK, FK_job_Opening_Id) VALUES (?,?)";
        PreparedStatement statement_application = con.prepareStatement(application_insert, Statement.RETURN_GENERATED_KEYS);
        statement_application.setInt(1, candidate_id);
        statement_application.setInt(2, (Integer) delegateExecution.getVariable("openingId"));
        statement_application.executeUpdate();
        ResultSet rs_application = statement_application.getGeneratedKeys();

        if (rs_application.next()){
            // set process variable required for next step
            delegateExecution.setVariable("applications_received", true);
            delegateExecution.setVariable("candidate_email", "wplacmrecruiting@gmail.com");
            delegateExecution.setVariable("candidate_confirmation_text", "hello this is a test");

            // add application to collection
            String application = "{\"application_id\" : \"" + rs_application.getInt(1) + "\","
                    + "\"candidate_id\" : \"" + candidate_id + "\","
                    + "\"first_name\" : \"" + candidate.getValue().prop("first_name").stringValue() + "\","
                    + "\"last_name\" : \"" + candidate.getValue().prop("last_name").stringValue() + "\","
                    + "\"cv\" :  \"" + "cv link" + "\""
                    + "}";

            System.out.println(application);
            SpinJsonNode application_json = JSON(application);

            JsonValueImpl collApplication = (JsonValueImpl) delegateExecution.getVariableTyped("collectedApplications");

            SpinJsonNode application_collection = JSON(collApplication.getValueSerialized());

            if (!application_collection.hasProp("applications")){
                application_collection.prop("applications", application_json);
            }
            else{
                application_collection.prop("applications").append(application_json);
            }
            delegateExecution.setVariable("collectedApplications", application_collection);
        }
        else {
            //error
        }
    }
}
