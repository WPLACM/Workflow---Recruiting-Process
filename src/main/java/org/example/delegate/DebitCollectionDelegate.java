package org.example.delegate;

import com.sun.mail.util.MailSSLSocketFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

//TODO WBIG mail address
//TODO Message Task instead of Service Task
//TODO Test if the given IBAN is correct?

public class DebitCollectionDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("debit", true);
        String lawyer_mail = "genericlawyer123@gmail.com"; //TODO replace this with WBIG mail
        String client_name = (String) delegateExecution.getVariable("client_name");
        String openingid = (String) delegateExecution.getVariable("openingid");
        String debit_message = "Dear "+client_name+ "," + System.lineSeparator() +
                "we have received your debit authorization for the job opening with the ID "
                 + openingid + ". Expect your specified account to be charged in the upcoming days." + System.lineSeparator() +
                 "Sincerely, WPLACM Headhunting";

        System.out.print(debit_message);

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
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(lawyer_mail)); //TODO replace with WBIG mail here
            message.setSubject("Your debit authorization is being processed");
            message.setText(debit_message);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}
}
}