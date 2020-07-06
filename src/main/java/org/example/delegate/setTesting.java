package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class setTesting implements JavaDelegate {

    public void execute(DelegateExecution delegateExecution) throws Exception {
        String new_client_company = "new_client_company_Test";
        delegateExecution.setVariable("new_client_company", new_client_company);
        //JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name = "client_name_Test";
        delegateExecution.setVariable("client_name", client_name);
        //String client_name = client_company.getValue().prop("name").stringValue();
        String new_job_opening_information = "new_job_opening_information_Test";
        delegateExecution.setVariable("new_job_opening_information", new_job_opening_information);
        //JsonValue job_opening_info = delegateExecution.getVariableTyped("new_job_opening_information");
        String wbig_process_id = "wbig_process_id_Test";
        delegateExecution.setVariable("wbig_process_id", wbig_process_id);
        //String wbig_process_id = job_opening_info.getValue().prop("WBIG_process_ID").stringValue();
        String time_stamp = "time_stamp_Test";
        delegateExecution.setVariable("time_stamp", time_stamp);
        //String time_stamp = job_opening_info.getValue().prop("time_stamp").stringValue();
        String job_opening = "job_opening_Test";
        delegateExecution.setVariable("job_opening", job_opening);
        //JsonValue job_opening = delegateExecution.getVariableTyped("new_job_opening");
        String openingid = "openingid_Test";
        delegateExecution.setVariable("openingid", openingid);
        //String openingid = job_opening.getValue().prop("job_opening_id").toString();
        Integer payment_info = 10;
        delegateExecution.setVariable("payment_info", payment_info);
        //int payment_info = Integer.parseInt(delegateExecution.getVariable("payment_information_acceptances").toString());
        Integer number_of_acceptances = 100;
        delegateExecution.setVariable("number_of_acceptances", number_of_acceptances);
        //String acceptances=delegateExecution.getVariable("number_of_acceptances").toString();
        String openingName = "openingName_Test";
        delegateExecution.setVariable("openingName", openingName);
        //String openingName = delegateExecution.getVariable("opening_name").toString();
        String processID = "processID_Test";
        delegateExecution.setVariable("processID", processID);
        //String processID=delegateExecution.getVariable("wplacm_id").toString();
        //Date Format yyyy MMM dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        Calendar calendar = Calendar.getInstance();
        String date = sdf.format(calendar.getTime());
        delegateExecution.setVariable("date", date);
    }
}
