package org.example.delegate;

import com.sun.mail.util.MailSSLSocketFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Properties;

public class ContactCandidatesDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        for(int i = 0; i < ((ArrayList<String>) execution.getVariable("candidates_email")).size(); i++){
            String message = ((ArrayList<String>) execution.getVariable("ListOfMails")).get(i);
            String mailAddress = ((ArrayList<String>) execution.getVariable("candidates_email")).get(i);

            //sendmail code
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
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(mailAddress));
                mimeMessage.setSubject("New Job Offer for you!");
                mimeMessage.setText(String.valueOf(message));

                //send message
                Transport.send(mimeMessage);
                System.out.println("message sent successfully");
            } catch (MessagingException e) {throw new RuntimeException(e);}

        }
        execution.setVariable("applications_received", false);

    }
}
