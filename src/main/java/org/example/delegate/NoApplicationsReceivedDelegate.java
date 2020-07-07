package org.example.delegate;

import camundajar.impl.com.google.gson.Gson;
import connectjar.org.apache.http.entity.StringEntity;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;



public class NoApplicationsReceivedDelegate implements JavaDelegate {

    public void execute(DelegateExecution delegateExecution) throws Exception {
        JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name = client_company.getValue().prop("name").stringValue();
        JsonValue job_opening_info = delegateExecution.getVariableTyped("new_job_opening_information");
        String wbig_process_id = job_opening_info.getValue().prop("WBIG_process_ID").stringValue();
        String time_stamp = job_opening_info.getValue().prop("time_stamp").stringValue();
        String noapplicationsJSON = "{\"WPLACM_process_ID\" : \"ID\","
                + "\"WBIG_process_ID\" : \"" + wbig_process_id + "\","
                + "\"time_stamp\" : \"" + time_stamp + "\","
                + "\"message\" : \" Dear " + client_name + ", we are very sorry that " +
                "we were not able to find any suitable candidates for you. Our apologies. " +
                "We hope we can continue doing services for you in the future for " +
                "any other job openings you will have. Thanks very much for your " +
                "understanding. Yours faithfully, WPLACM. \"}";

        JsonValue jsonValue = SpinValues.jsonValue(noapplicationsJSON).create(); //might be irrelevant
        delegateExecution.setVariable("NoApplicationsReceivedMessage", jsonValue);

        System.out.println("HTTP POST Start"); //Just to test, if the json posting works.

        HttpConnector http = Connectors.getConnector(HttpConnector.ID);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(new Gson().toJson(jsonValue)); //gson.tojson() converts your pojo to json

        http.createRequest()
                .post()
                .url("http://localhost:8080/WPLACM/rest/request/send_noapplicationsreceivedmsg") //update for WBIG
                .contentType("application/json")
                .payload(String.valueOf(postingString))
                .execute();

    }
}
