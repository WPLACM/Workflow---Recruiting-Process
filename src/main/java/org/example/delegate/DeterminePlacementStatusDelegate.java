package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;


public class DeterminePlacementStatusDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String job_opening = (String) delegateExecution.getVariable("new_job_opening_information");
        // JsonValue job_opening = delegateExecution.getVariableTyped("new_job_opening_information");
        Integer open_spots = (Integer) delegateExecution.getVariable("open_spots");
        //int open_spots = Integer.parseInt(job_opening.getValue().prop("open_spots").stringValue());
        Integer number_acceptances = (Integer) delegateExecution.getVariable("number_of_acceptances");
        //int number_acceptances = Integer.parseInt(delegateExecution.getVariable("number_of_acceptances").toString());
        Integer new_open = open_spots - number_acceptances;
        if (new_open > 0) {
            delegateExecution.setVariable("placementFin",  false);
            delegateExecution.setVariable("open_spots", new_open);
            System.out.print("false "+delegateExecution.getVariable("open_spots"));
        } else {
            delegateExecution.setVariable("placementFin", true);
            delegateExecution.setVariable("open_spots", new_open);
            System.out.print("true "+delegateExecution.getVariable("open_spots"));
          }
        }
    }
