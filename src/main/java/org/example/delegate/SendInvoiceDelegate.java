package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.h2.util.json.JSONValue;

//TODO set invoice ID
//TODO replace values with actual properties
//TODO testing

public class SendInvoiceDelegate implements JavaDelegate {

    private int number_of_dunns;
    public void setNumber_of_dunns(int dunn){this.number_of_dunns=dunn;}

    public void execute(DelegateExecution delegateExecution) throws Exception {
        JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name = client_company.getValue().prop("name").stringValue();
        JsonValue job_opening = delegateExecution.getVariableTyped("new_job_opening");
        String opening_id=job_opening.getValue().prop("job_opening_id").stringValue();
        int paymentInfo = Integer.parseInt(delegateExecution.getVariable("paymentInformationAcceptances").toString());
        String net=delegateExecution.getVariable("net").toString();
        String gross=delegateExecution.getVariable("gross").toString();
        String tax=delegateExecution.getVariable("tax").toString();
        String acceptances=delegateExecution.getVariable("number_of_acceptances").toString();

        String invoiceJSON = "{\"WPLACM_process_ID\" : \"ID\","
                + "\"WBIG_process_ID\" : \"ID\","
                + "\"timestamp\" : 1234\","
                + "\"Invoice_ID\" : \"ID\","
                + "\"Payment_information_acceptances\" : " +paymentInfo+ ","
                + "\"Date\" : \"date\","
                + "\"Tax_id_WPLACM\" : 1234\","
                + "" +client_name+ ": {"
                    + "\"street\" : \"example\","
                    + "\"post code\" : 1234"
                    + "}"
                +"\"address WPLACM\" : {"
                    + "\"street\" : \"Leonardo-Campus 3\","
                    + "\"post code\" : 48149"
                    + "}"
                +"\"Number_of_acceptances\" : " +acceptances+ ","
                +"\"Opening_ID\" : \"ID\","
                +"\"Opening_Name\" : \"name\","
                +"\"Gross\" : " +gross+ ","
                +"\"Net\" : " +net+ ","
                +"\"Tax\" : " +tax+ "}";
        this.number_of_dunns=0;
        delegateExecution.setVariable("invoice", invoiceJSON);
    }

}
