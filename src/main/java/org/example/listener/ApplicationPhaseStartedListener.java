package org.example.listener;

import camundajar.impl.com.google.gson.JsonArray;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.spin.json.SpinJsonNode;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.example.entity.Application;
import org.example.model.ApplicationCollectionElement;
import static org.camunda.spin.Spin.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ApplicationPhaseStartedListener implements ExecutionListener {

    public void notify(DelegateExecution execution) throws Exception {
        /*
        Collection<Integer> collectedApplications = new ArrayList();
        execution.setVariable("collectedApplications", collectedApplications );
         */

        /*
        Collection<ApplicationCollectionElement> collectedApplications = new ArrayList();

        ObjectValue typedObjectValue = Variables
                .objectValue(collectedApplications)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();

        execution.setVariable("collectedApplications", typedObjectValue );

         */

        // create json
        String applications = "{ \"applications\": [] }";
        SpinJsonNode json = JSON(applications);
        execution.setVariable("collectedApplications", json );

    }
}
