package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.value.JsonValue;

import javax.mail.*;
import javax.mail.internet.*;
import com.sun.mail.util.MailSSLSocketFactory;

import java.security.GeneralSecurityException;
import java.util.Properties;

public class  SendConfirmationDelegate implements JavaDelegate{
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        // get email from candidate JSON process variable
        JsonValue candidate = delegateExecution.getVariableTyped("new_candidate");
        String email_address = candidate.getValue().prop("email").stringValue();

        String message_str = "Dear " + candidate.getValue().prop("first_name").stringValue() + " " +
                                   candidate.getValue().prop("last_name").stringValue() + ", " + System.lineSeparator() +
                         "we have received your application " + /* "number " + applicationid + */
                         "for the Job Offer " + delegateExecution.getVariable("openingId") +
                         " - \'" + delegateExecution.getVariable("jobTitle") + "\'." + System.lineSeparator() +
                         "Please be patient, we will inform you regarding your application status." +
                         System.lineSeparator() + "Sincerely, WPLACM Headhunting";

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
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(email_address));
            message.setSubject("Your Application has been received");
            /*
            String msg = "";
            if (msgtype == 1) {
                msg = String.format(REJECTION_MSG, applicant, topic);
            } else {
                msg = String.format(INVITATION_MSG, applicant, topic, optionalUrl);
            }
            */
            message.setText(message_str);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}

    }
}
