package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class DeterminePlacementStatusDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String job_opening = (String) delegateExecution.getVariable("new_job_opening_information");
        Integer open_spots = (Integer) delegateExecution.getVariable("open_spots");
        Integer number_acceptances = (Integer) delegateExecution.getVariable("number_of_acceptances");
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
