package org.example.delegate;

import com.sun.mail.util.MailSSLSocketFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.h2.util.json.JSONValue;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

//TODO testing
// TODO replace opening ID, client company name with actual properties set by Maxi + Luis

public class SendDunnDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String lawyer_mail = "genericlawyer123@gmail";
        int dunn = (int) delegateExecution.getVariable("number_of_dunns");
        dunn=dunn+1;
        delegateExecution.setVariable("number_of_dunns", dunn);
        JsonValue client_company = delegateExecution.getVariableTyped("new_client_company");
        String client_name=client_company.getValue().prop("name").stringValue();
        JsonValue job_opening = delegateExecution.getVariableTyped("new_job_opening");
        String opening_id=job_opening.getValue().prop("job_opening_id").stringValue();
        String dunn_message = "Dear Mr. Lawyer," + System.lineSeparator() +
                "we are sadly running into payment issues with " + client_name +
                " for the job opening with ID " + opening_id + "." + System.lineSeparator() +
                "Please initiate the dunning process." +System.lineSeparator() + "Sincerely, WPLACM Headhunting";

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.checkserveridentity", "false");
        props.put("mail.smtp.ssl.trust", "*");

        MailSSLSocketFactory socketFactory;
        try {
            socketFactory = new MailSSLSocketFactory();
            socketFactory.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.socketFactory", socketFactory);
        } catch (GeneralSecurityException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("wplacmrecruiting@gmail.com","Supersecret2020");
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            //genericlawyer123@gmail.com
            //supersecret2020
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(lawyer_mail));
            message.setSubject("Payment issue requires dunning");
            message.setText(dunn_message);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}
    }
}
