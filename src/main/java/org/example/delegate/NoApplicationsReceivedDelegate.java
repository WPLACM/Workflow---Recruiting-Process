package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.NoApplicationsReceivedMessage;
import org.springframework.web.client.RestTemplate;
import org.example.utility.wbigRestEndpoints;

public class NoApplicationsReceivedDelegate implements JavaDelegate {

    public void execute(DelegateExecution delegateExecution) throws Exception {
        RestTemplate template = new RestTemplate();
        String wbig_processInstanceId = (String) delegateExecution.getVariable("wbig_processInstanceId");
        String time_stamp = (String) delegateExecution.getVariable("time_stamp");
        String textmessage = "Dear WBIG, we are very sorry that we were not able to find any suitable candidates " +
                "for you. Our apologies. We hope we can continue doing services for you in the future for any " +
                "other job openings you will have. Thanks very much for your understanding. Yours faithfully, WPLACM.";

        NoApplicationsReceivedMessage noappmsg = new NoApplicationsReceivedMessage(time_stamp, textmessage);

        // sends data-object to url (String class specification needed)
        String EndpointUrl = new wbigRestEndpoints().getCurrent_URL();
        String wplacm_processInstanceId = template.postForObject(EndpointUrl + "wbig/wbig_nocvs/" + wbig_processInstanceId, noappmsg, String.class);


        /** old version for sending via http
        JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name = client_company.getValue().prop("name").stringValue();
        JsonValue job_opening_info = delegateExecution.getVariableTyped("new_job_opening_information");
        String wbig_processInstanceId = job_opening_info.getValue().prop("wbig_processInstanceId").stringValue();
        String time_stamp = job_opening_info.getValue().prop("time_stamp").stringValue();
        String noapplicationsJSON = "{\"WPLACM_process_ID\" : \"ID\","
                + "\"WBIG_process_ID\" : \"" + wbig_processInstanceId + "\","
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
         **/

    }
}
