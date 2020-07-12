package org.example.delegate;
;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.Invoice;
import org.example.utility.wbigRestEndpoints;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;


//TODO set WBIG address

public class SendInvoiceDelegate implements JavaDelegate {

    public void execute(DelegateExecution delegateExecution) throws Exception {
        RestTemplate template = new RestTemplate();
        String client_company = (String) delegateExecution.getVariable("new_client_company");
        String client_name = (String) delegateExecution.getVariable("client_name");
        String job_opening_info = (String) delegateExecution.getVariable("job_opening_info");
        String wbig_process_id = (String) delegateExecution.getVariable("wbig_process_id");
        String time_stamp = (String) delegateExecution.getVariable("time_stamp");
        String job_opening = (String) delegateExecution.getVariable("job_opening");
        String openingid = (String) delegateExecution.getVariable("openingid");
        String invoiceid = (String) delegateExecution.getVariable("invoiceid");
        Double payment_information_acceptances = (Double) delegateExecution.getVariable("payment_info");
        Double net = (Double) delegateExecution.getVariable("net");
        Double gross = (Double) delegateExecution.getVariable("gross");
        Double tax = (Double) delegateExecution.getVariable("tax");
        Integer number_of_acceptances = (Integer) delegateExecution.getVariable("number_of_acceptances");
        String openingName = (String) delegateExecution.getVariable("openingName");
        String processID = (String) delegateExecution.getVariable("processID");
        delegateExecution.setVariable("number_of_dunns", 0);
        String address_send = "Leonardo Campus 3, 48149 Münster";
        String address_rec = "WBIG street 5, 48149 Münster";
        String taxID = "AB123456";

        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        //String date = (String) delegateExecution.getVariable("date");

        String wbig_processInstanceId = (String) delegateExecution.getVariable("wbig_processInstanceId");

        Invoice inv = new Invoice(delegateExecution.getProcessInstanceId(), invoiceid, payment_information_acceptances, date, taxID, address_rec, address_send, number_of_acceptances, openingid,
                openingName, gross, net, tax);

        String EndpointUrl = new wbigRestEndpoints().getCurrent_URL();
        wbig_processInstanceId = template.postForObject(EndpointUrl + "wbig/wbig_billingSuff/" + wbig_processInstanceId, inv, String.class);
        //delegateExecution.setVariable("wbig_processInstanceId", wbig_processInstanceId);

        /*
        String invoiceJSON = "{\"WPLACM_process_ID\" : \"" +processID+"\","
                + "\"WBIG_process_ID\" : \"" +wbig_process_id+ "\","
                + "\"timestamp\" : \"" +time_stamp+"\","
                + "\"Invoice_ID\" : \"" +invoiceid+"\","
                + "\"Payment_information_acceptances\" : \"" +payment_info+ ","
                + "\"Date\" : \"" +date+ "\","
                + "\"Tax_id_WPLACM\" : \"AB123456\","
                + "\"" +client_name+"\" : {"
                    + "\"street\" : \"WBIG street 1\","
                    + "\"post code\" : \"12345\""
                    + "}"
                +"\"address WPLACM\" : {"
                    + "\"street\" : \"Leonardo-Campus 3\","
                    + "\"post code\" : \"48149\""
                    + "}"
                +"\"Number_of_acceptances\" : \"" +number_of_acceptances+ "\","
                +"\"Opening_ID\" : \"" +openingid+ "\","
                +"\"Opening_Name\" : \"" +openingName+"\","
                +"\"Gross\" : \"" +gross+ "\","
                +"\"Net\" : \"" +net+ "\","
                +"\"Tax\" : \"" +tax+ "\"}";
*/

    }

}
