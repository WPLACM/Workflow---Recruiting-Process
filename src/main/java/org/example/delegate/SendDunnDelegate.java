package org.example.delegate;

import camundajar.impl.com.google.gson.Gson;
import com.sun.mail.util.MailSSLSocketFactory;
import connectjar.org.apache.http.entity.StringEntity;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

//TODO Decide if the dunn goes to WBIG or the Lawyer
// TODO replace opening ID, client company name with actual properties set by Maxi + Luis


public class SendDunnDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //String lawyer_mail = "genericlawyer123@gmail.com";
        Integer dunn = (Integer) delegateExecution.getVariable("number_of_dunns");
        dunn=dunn+1;
        delegateExecution.setVariable("number_of_dunns", dunn);
        String client_company = (String) delegateExecution.getVariable("new_client_company");
        String client_name = (String) delegateExecution.getVariable("client_name");
        String job_opening_info = (String) delegateExecution.getVariable("job_opening_info");
        String openingid = (String) delegateExecution.getVariable("openingid");
        //TODO Create JSON if this goes to WBIG.
        String dunn_message = "Dear WBIG," + System.lineSeparator() +
                "we sadly did not receive your debit authorization " +
                "for the job opening with ID " + openingid + "." + System.lineSeparator() +
                "This is the " +dunn+ ". dunn we sent."+ System.lineSeparator() +
                "Please initiate the debit authorization. We do not want to be forced to take legal actions." +System.lineSeparator() + System.lineSeparator() +
                "Thank You"+System.lineSeparator() +
                "Sincerely, WPLACM Headhunting";

        System.out.print(dunn_message);

        JsonValue jsonValue = SpinValues.jsonValue(dunn_message).create(); //might be irrelevant
        delegateExecution.setVariable("dunn_message", jsonValue);

        System.out.println("HTTP POST Start"); //Just to test, if the json posting works.

        HttpConnector http = Connectors.getConnector(HttpConnector.ID);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(new Gson().toJson(jsonValue)); //gson.tojson() converts your pojo to json

        http.createRequest()
                .post()
                .url("http://localhost:8080/engine-rest/message/message_name") //TODO update for WBIG
                .contentType("application/json")
                .payload(String.valueOf(postingString))
                .execute();
        //Message
//        delegateExecution.getProcessEngineServices().getRuntimeService()
//                .createMessageCorrelation("message_name")//TODO message name of WBIG
//                .setVariable("dunn_message", dunn_message)
//                .correlate();
//
//        Properties props = new Properties();
//
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.checkserveridentity", "false");
//        props.put("mail.smtp.ssl.trust", "*");
//
//        MailSSLSocketFactory socketFactory;
//        try {
//            socketFactory = new MailSSLSocketFactory();
//            socketFactory.setTrustAllHosts(true);
//            props.put("mail.smtp.ssl.socketFactory", socketFactory);
//        } catch (GeneralSecurityException e1) {
//            e1.printStackTrace();
//        }
//
//        //get Session
//        Session session = Session.getDefaultInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication("wplacmrecruiting@gmail.com","Supersecret2020");
//                    }
//                });
//        //compose message
//        try {
//            MimeMessage message = new MimeMessage(session);
//            //genericlawyer123@gmail.com
//            //supersecret2020
//            message.addRecipient(Message.RecipientType.TO,new InternetAddress(lawyer_mail));
//            message.setSubject("Payment issue requires dunning");
//            message.setText(dunn_message);
//            //send message
//            Transport.send(message);
//            System.out.println("message sent successfully");
//        } catch (MessagingException e) {throw new RuntimeException(e);}
    }
}
