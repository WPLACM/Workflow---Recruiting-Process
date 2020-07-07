package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class setTesting implements JavaDelegate {

    public void execute(DelegateExecution delegateExecution) throws Exception {
        String new_client_company = "new_client_company_Test";
        delegateExecution.setVariable("new_client_company", new_client_company);
        String client_name = "client_name_Test";
        delegateExecution.setVariable("client_name", client_name);
        String new_job_opening_information = "new_job_opening_information_Test";
        delegateExecution.setVariable("new_job_opening_information", new_job_opening_information);
        String wbig_process_id = "wbig_process_id_Test";
        delegateExecution.setVariable("wbig_process_id", wbig_process_id);
        String time_stamp = "time_stamp_Test";
        delegateExecution.setVariable("time_stamp", time_stamp);
        String job_opening = "job_opening_Test";
        delegateExecution.setVariable("job_opening", job_opening);
        String openingid = "openingid_Test";
        delegateExecution.setVariable("openingid", openingid);
        Integer payment_info = 10;
        delegateExecution.setVariable("payment_info", payment_info);
        Integer number_of_acceptances = 100;
        delegateExecution.setVariable("number_of_acceptances", number_of_acceptances);
        String openingName = "openingName_Test";
        delegateExecution.setVariable("openingName", openingName);
        String processID = "processID_Test";
        delegateExecution.setVariable("processID", processID);
        Integer open_spots = 101;
        delegateExecution.setVariable("open_spots", open_spots);
        //Invoice Date Format yyyy MMM dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        Calendar calendar = Calendar.getInstance();
        String date = sdf.format(calendar.getTime());
        delegateExecution.setVariable("date", date);
    }
}
