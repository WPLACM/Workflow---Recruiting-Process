package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

//TODO testing

public class DeterminePlacementStatusDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        JsonValue job_opening = delegateExecution.getVariableTyped("new_client_company");
        int open_spots = Integer.parseInt(job_opening.getValue().prop("openSpots").stringValue());
        int number_acceptances = Integer.parseInt(delegateExecution.getVariable("number_of_acceptances").toString());
        boolean placementFin = false;

            if (open_spots - number_acceptances > 0) {
                placementFin = false;
            } else {
                placementFin = true;
            }
        }
    }
