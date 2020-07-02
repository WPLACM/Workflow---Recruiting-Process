package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

public class SendInvoiceDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name = client_company.getValue().prop("name").stringValue();

        String invoiceJSON = "{\"WPLACM_process_ID\" : \"ID\","
                + "\"WBIG_process_ID\" : \"ID\","
                + "\"timestamp\" : 1234\","
                + "\"Invoice_ID\" : \"ID\","
                + "\" Payment_information_acceptances\" : \"info\","
                + "\" Date\" : \"date\","
                + "\" Tax_id_WPLACM\" : 1234\","
                + "\"addressWBIG\" : {"
                    + "\"street\" : \"example\","
                    + "\"post code\" : 1234"
                    + "}"
                + "\"addressWPLACM\" : {"
                    + "\"street\" : \"example\","
                    + "\"post code\" : 1234"
                    + "}"
                +"\"Number_of_acceptances\" : \"number\", "
                +"\" Opening_ID\" : \"ID\","
                +"\" Opening_Name\" : \"name\","
                +"\"Gross\" : \"amount\","
                +"\"Net\" : \"amount\","
                +"\"Tax\" : \"amount\""
                + "}";
    }

}
