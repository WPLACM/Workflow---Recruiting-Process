package org.example.delegate;
;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.Invoice;
import java.util.Date;
import org.springframework.web.client.RestTemplate;


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


        Date cdate = (Date) delegateExecution.getVariable("date");

        Invoice inv = new Invoice(delegateExecution.getProcessInstanceId(), invoiceid, payment_information_acceptances, cdate, taxID, address_rec, address_send, number_of_acceptances, openingid,
                openingName, gross, net, tax);
        String wplacm_processInstanceId = template.postForObject("http://localhost:8080/wbig/wbig", inv, String.class);
        delegateExecution.setVariable("wplacm_processInstanceId", wplacm_processInstanceId);

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
        //TODO Send Java Object

    }

}
