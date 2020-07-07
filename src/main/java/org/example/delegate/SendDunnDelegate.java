package org.example.delegate;

import com.sun.mail.util.MailSSLSocketFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

// TODO replace opening ID, client company name with actual properties set by Maxi + Luis


public class SendDunnDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String lawyer_mail = "genericlawyer123@gmail.com";
        Integer dunn = (Integer) delegateExecution.getVariable("number_of_dunns");
        dunn=dunn+1;
        delegateExecution.setVariable("number_of_dunns", dunn);
        String client_company = (String) delegateExecution.getVariable("new_client_company");
        String client_name = (String) delegateExecution.getVariable("client_name");
        String job_opening_info = (String) delegateExecution.getVariable("job_opening_info");
        String openingid = (String) delegateExecution.getVariable("openingid");
        String invoiceid = (String) delegateExecution.getVariable("invoiceid");
        String dunn_message = "Dear WBIG," + System.lineSeparator() +
                "we sadly did not receive your debit authorization from the Invoice " + invoiceid +
                " for the job opening with ID " + openingid + "." + System.lineSeparator() +
                "This is the " +dunn+ ". dunn we sent."+ System.lineSeparator() +
                "Please initiate the debit authorization within the next 7 days. We do not want to be forced to take legal actions." +System.lineSeparator() + System.lineSeparator() +
                "Thank You"+System.lineSeparator() +
                "Sincerely, WPLACM Headhunting"+System.lineSeparator();

        System.out.print(dunn_message);

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
