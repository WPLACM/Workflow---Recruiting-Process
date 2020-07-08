package org.example.delegate;

import com.sun.mail.util.MailSSLSocketFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;


public class TakeLegalActionDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String lawyer_mail = "genericlawyer123@gmail.com";
        String client_company = (String) delegateExecution.getVariable("new_client_company");
        String client_name = (String) delegateExecution.getVariable("client_name");
        String job_opening_info = (String) delegateExecution.getVariable("job_opening_info");
        String openingid = (String) delegateExecution.getVariable("openingid");
        String legalaction_message = "Dear Mr. Lawyer," + System.lineSeparator() +
                "our client " + client_name + " is not paying our invoice, even after sending him three dunns, " +
                "for the job opening with the ID " +openingid + "." + System.lineSeparator() +
                "Please take legal action." +System.lineSeparator() +
                "Sincerely, WPLACM Headhunting"+System.lineSeparator();

        System.out.print(legalaction_message);
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
            message.setSubject("Payment issue requires legal action");
            message.setText(legalaction_message);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}


    }
}
