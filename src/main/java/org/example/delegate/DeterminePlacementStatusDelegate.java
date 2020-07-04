package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

//TODO testing

public class DeterminePlacementStatusDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        JsonValue job_opening = delegateExecution.getVariableTyped("new_job_opening_information");
        int open_spots = Integer.parseInt(job_opening.getValue().prop("open_spots").stringValue());
        int number_acceptances = Integer.parseInt(delegateExecution.getVariable("number_of_acceptances").toString());

            if (open_spots - number_acceptances > 0) {
                delegateExecution.setVariable("placementFin",  false);
            } else {
               delegateExecution.setVariable("placementFin", true);
            }
        }
    }
