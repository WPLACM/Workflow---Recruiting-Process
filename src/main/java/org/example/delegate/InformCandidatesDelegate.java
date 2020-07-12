package org.example.delegate;

import com.sun.mail.util.MailSSLSocketFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.extension.mail.delete.DeleteMailConnector;
import org.example.entity.ApplicationMessage;
import org.example.entity.ApplicationMessageList;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InformCandidatesDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
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

        // get candidate collection
        ObjectValue typedApplicationCollection =
                (ObjectValue) delegateExecution.getVariableTyped("finalSelection");

        ApplicationMessageList final_selection = (ApplicationMessageList) typedApplicationCollection.getValue();
        Integer openingId = (Integer) delegateExecution.getVariable("openingId");

        // create flat list of forwarded candidates
        List<Integer> fwded_applications = new ArrayList<Integer>();
        for( ApplicationMessage app : final_selection.getApplicationList() ){
            Integer fwd_application_id = app.getApplicant_id();
            fwded_applications.add(fwd_application_id);
        }
        // all applications for this job opening
        String applications_query = "SELECT A.application_id, C.first_name, C.last_name, C.email " +
                "FROM Application A " +
                "INNER JOIN Candidate C ON (C.candidate_id = A.ca_ap_fk) " +
                "WHERE FK_job_Opening_Id = " + Integer.toString(openingId);

        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        Statement query = con.createStatement();
        ResultSet rs = query.executeQuery(applications_query);

        String email_address = "";
        String recipient_name = "";
        String subject = "";
        String message_text = "";
        while(rs.next()) {
            // send mail A to forwarded candidates
            recipient_name = rs.getString("first_name") + " " + rs.getString("last_name");
            if (fwded_applications.contains(rs.getInt("application_id"))){
                subject = "Your Application has been forwarded to the offering Company!";
                message_text = "Dear " + recipient_name + ", " + System.lineSeparator() +
                        "we have forwarded your application " +
                        "for the Job Offer " + delegateExecution.getVariable("openingId") +
                        " - \'" + delegateExecution.getVariable("jobTitle") + "\'." + System.lineSeparator() +
                        "Please be patient, the company offering this position will contact you soon." +
                        System.lineSeparator() + "Sincerely, WPLACM Headhunting";
            }
            // send mail B to rejected candidates
            else {
                subject = "Your Application has been rejected";
                message_text = "Dear " + recipient_name + ", " + System.lineSeparator() +
                        "unfortunately we have to inform you, that your application " +
                        "for the Job Offer " + delegateExecution.getVariable("openingId") +
                        " - \'" + delegateExecution.getVariable("jobTitle") + "\' has been rejected." + System.lineSeparator() +
                        "For other interesting job offers, please consider visiting our website." +
                        System.lineSeparator() + "Sincerely, WPLACM Headhunting";
            }
            email_address = rs.getString("email");

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
                message.setSubject(subject);
                message.setText(message_text);
                //send message
                Transport.send(message);
                System.out.println("message sent successfully");
            } catch (MessagingException e) {throw new RuntimeException(e);}

        }

    }
}
