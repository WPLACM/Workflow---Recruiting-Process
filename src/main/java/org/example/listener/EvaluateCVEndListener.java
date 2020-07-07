package org.example.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.feel.syntaxtree.In;
import org.camunda.spin.json.SpinJsonNode;
import org.camunda.spin.plugin.variable.value.impl.JsonValueImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static org.camunda.spin.Spin.JSON;
import static org.camunda.spin.Spin.S;

public class EvaluateCVEndListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

        JsonValueImpl applicationelem = (JsonValueImpl) delegateExecution.getVariableTyped("applicationelem");

        SpinJsonNode application = JSON(applicationelem.getValueSerialized());

        String s_cv_rating = (String) delegateExecution.getVariable("cv_rating");

        String s_application_id = application.prop("application_id").stringValue();

        Integer cv_rating = Integer.parseInt(s_cv_rating);
        Integer application_id = Integer.parseInt(s_application_id);

        // update db
        // Prepare Update Statement
        String application_update =
                "UPDATE Application  SET " +
                        "cv_rating = " + cv_rating + " " +
                        "WHERE application_id = " + application_id;

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        PreparedStatement statement = con.prepareStatement(application_update);
        Integer index = statement.executeUpdate();

        delegateExecution.setVariable("cv_rating", "");

    }
}
