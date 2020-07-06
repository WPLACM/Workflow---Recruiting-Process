package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.h2.util.json.JSONValue;


//TODO set invoice ID, WBIG address
//TODO testing

public class SendInvoiceDelegate implements JavaDelegate {

    public void execute(DelegateExecution delegateExecution) throws Exception {
        String client_company = (String) delegateExecution.getVariable("new_client_company");
        //JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name = (String) delegateExecution.getVariable("client_name");
        //String client_name = client_company.getValue().prop("name").stringValue();
        String job_opening_info = (String) delegateExecution.getVariable("job_opening_info");
        //JsonValue job_opening_info = delegateExecution.getVariableTyped("new_job_opening_information");
        String wbig_process_id = (String) delegateExecution.getVariable("wbig_process_id");
        //String wbig_process_id = job_opening_info.getValue().prop("WBIG_process_ID").stringValue();
        String time_stamp = (String) delegateExecution.getVariable("time_stamp");
        //String time_stamp = job_opening_info.getValue().prop("time_stamp").stringValue();
        String job_opening = (String) delegateExecution.getVariable("job_opening");
        //JsonValue job_opening = delegateExecution.getVariableTyped("new_job_opening");
        String openingid = (String) delegateExecution.getVariable("openingid");
        //String openingid = job_opening.getValue().prop("job_opening_id").toString();
        Integer payment_info = (Integer) delegateExecution.getVariable("payment_info");
        //int paymentInfo = Integer.parseInt(delegateExecution.getVariable("payment_information_acceptances").toString());
        Integer net = (Integer) delegateExecution.getVariable("net");
        //String net=delegateExecution.getVariable("net").toString();
        Double gross = (Double) delegateExecution.getVariable("gross");
        //String gross=delegateExecution.getVariable("gross").toString();
        Double tax = (Double) delegateExecution.getVariable("tax");
        //String tax=delegateExecution.getVariable("tax").toString();
        Integer number_of_acceptances = (Integer) delegateExecution.getVariable("number_of_acceptances");
        //String number_of_acceptances=delegateExecution.getVariable("number_of_acceptances").toString();
        String openingName = (String) delegateExecution.getVariable("openingName");
        //String openingName = delegateExecution.getVariable("opening_name").toString();
        String processID = (String) delegateExecution.getVariable("processID");
        //String processID=delegateExecution.getVariable("wplacm_id").toString();

        String date = (String) delegateExecution.getVariable("date");

                String invoiceJSON = "{\"WPLACM_process_ID\" : " +processID+","
                + "\"WBIG_process_ID\" : " +wbig_process_id+ ","
                + "\"timestamp\" : " +time_stamp+","
                + "\"Invoice_ID\" : \"ID\","
                + "\"Payment_information_acceptances\" : " +payment_info+ ","
                + "\"Date\" : " +date+ ","
                + "\"Tax_id_WPLACM\" : 123456\","
                + "" +client_name+ ": {"
                    + "\"street\" : \"WBIG street 1\","
                    + "\"post code\" : 1234"
                    + "}"
                +"\"address WPLACM\" : {"
                    + "\"street\" : \"Leonardo-Campus 3\","
                    + "\"post code\" : 48149"
                    + "}"
                +"\"Number_of_acceptances\" : " +number_of_acceptances+ ","
                +"\"Opening_ID\" : " +openingid+ ","
                +"\"Opening_Name\" : " +openingName+","
                +"\"Gross\" : " +gross+ ","
                +"\"Net\" : " +net+ ","
                +"\"Tax\" : " +tax+ "}";
        delegateExecution.setVariable("number_of_dunns", 0);
        delegateExecution.setVariable("invoice", invoiceJSON);
    }

}
