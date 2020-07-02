package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

public class  SendConfirmationDelegate implements JavaDelegate{
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        // get email from candidate JSON process variable
        JsonValue candidate = delegateExecution.getVariableTyped("new_candidate");
        String email_address = candidate.getValue().prop("email").stringValue();

        String message = "Dear " + candidate.getValue().prop("first_name").stringValue() + " " +
                                   candidate.getValue().prop("last_name").stringValue() + ", " + System.lineSeparator() +
                         "we have received your Application " + /* "number " + applicationid + */
                         "for the Job Offer" + /* job offer number + caption + */ "." + System.lineSeparator() +
                         "Please be patient, the Company WBIG will contact you regarding your application status." +
                         System.lineSeparator() + "Sincerely, WPLACM Headhunting";
        System.out.println(message);
        // send "application received" email
        // see:
        //https://blog.camunda.com/post/2016/06/camunda-bpm-mail/
    }
}
