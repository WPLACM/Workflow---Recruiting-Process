package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.h2.util.json.JSONValue;

//TODO set invoice ID, date, WBIG address
//TODO testing

public class SendInvoiceDelegate implements JavaDelegate {

    public void execute(DelegateExecution delegateExecution) throws Exception {
        JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name = client_company.getValue().prop("name").stringValue();
        JsonValue job_opening_info = delegateExecution.getVariableTyped("new_job_opening_information");
        String wbig_process_id = job_opening_info.getValue().prop("WBIG_process_ID").stringValue();
        String time_stamp = job_opening_info.getValue().prop("time_stamp").stringValue();
        JsonValue job_opening = delegateExecution.getVariableTyped("new_job_opening");
        String openingid = job_opening.getValue().prop("job_opening_id").toString();
        int paymentInfo = Integer.parseInt(delegateExecution.getVariable("payment_information_acceptances").toString());
        String net=delegateExecution.getVariable("net").toString();
        String gross=delegateExecution.getVariable("gross").toString();
        String tax=delegateExecution.getVariable("tax").toString();
        String acceptances=delegateExecution.getVariable("number_of_acceptances").toString();
        String openingName = delegateExecution.getVariable("opening_name").toString();
        String processID=delegateExecution.getVariable("wplacm_id").toString();

        String invoiceJSON = "{\"WPLACM_process_ID\" : " +processID+","
                + "\"WBIG_process_ID\" : " +wbig_process_id+ ","
                + "\"timestamp\" : " +time_stamp+","
                + "\"Invoice_ID\" : \"ID\","
                + "\"Payment_information_acceptances\" : " +paymentInfo+ ","
                + "\"Date\" : \"date\","
                + "\"Tax_id_WPLACM\" : 123456\","
                + "" +client_name+ ": {"
                    + "\"street\" : \"WBIG street 1\","
                    + "\"post code\" : 1234"
                    + "}"
                +"\"address WPLACM\" : {"
                    + "\"street\" : \"Leonardo-Campus 3\","
                    + "\"post code\" : 48149"
                    + "}"
                +"\"Number_of_acceptances\" : " +acceptances+ ","
                +"\"Opening_ID\" : " +openingid+ ","
                +"\"Opening_Name\" : " +openingName+","
                +"\"Gross\" : " +gross+ ","
                +"\"Net\" : " +net+ ","
                +"\"Tax\" : " +tax+ "}";
        delegateExecution.setVariable("number_of_dunns", 0);
        delegateExecution.setVariable("invoice", invoiceJSON);
    }

}
