package org.example.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.example.model.Invoice;
import org.example.model.NumberOfCandidates;
import org.example.model.DebitAuthorization;

import java.sql.SQLException;
import java.util.logging.Logger;

@RestController
//creates "mailbox" to send to, relative to root path
@RequestMapping("/Billing")
public class Controller {

    private final java.util.logging.Logger LOGGER = Logger.getLogger(Controller.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    // specifes mailbox path, {id} to correlate with specific process instance
    @PostMapping(path = "/start/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String continueBillingProcess (@RequestBody NumberOfCandidates candidateInfo, @PathVariable ("id") String wbig_processInstanceId) throws SQLException {

        LOGGER.info("Controller WBIG ProcessInstanceId: " + wbig_processInstanceId);

        //correlation specification via message name "SomeCVs". This needs to be inserted as message name for catching event in bpmn-model.
        runtimeService.createMessageCorrelation("NumberOfCandidatesMessage")
                .processInstanceVariableEquals("wbig_processInstanceId", wbig_processInstanceId)
                .setVariable("number_of_acceptances", candidateInfo.getNumber_of_acceptances())
                .setVariable("payment_info", candidateInfo.getPayment_info())
                .processInstanceId(wbig_processInstanceId)
                .correlate();


        LOGGER.info("WPLACM Billing started");
        return "Process started";
    }

    public String test (@RequestBody Invoice invInfo, @PathVariable ("id") String wplacm_processInstanceId) throws SQLException {
        LOGGER.info("Controller WPLACM ProcessInstanceId: " + wplacm_processInstanceId);

        //correlation specification via message name "test_message". This needs to be inserted as message name for catching event in bpmn-model.
        runtimeService.createMessageCorrelation("test_message")
                .processInstanceVariableEquals("wplacm_processInstanceId", wplacm_processInstanceId)
                .setVariable("invoice_id", invInfo.getInvoice_id())
                .setVariable("payment_information_acceptances", invInfo.getPayment_information_acceptances() )
                .setVariable("invoice_date" , invInfo.getInvoice_date())
                .setVariable("tax_id" , invInfo.getTax_id())
                .setVariable("address_recipient", invInfo.getAddress_recipient())
                .setVariable("address_sender" , invInfo.getAddress_sender())
                .setVariable("number_of_acceptances", invInfo.getNumber_of_acceptances())
                .setVariable("opening_id" , invInfo.getOpening_id())
                .setVariable("opening_name" , invInfo.getOpening_name())
                .setVariable("gross" , invInfo.getGross())
                .setVariable("net" , invInfo.getNet())
                .setVariable("sales_tax" , invInfo.getSales_tax())
                .processInstanceId(wplacm_processInstanceId)
                .correlate();


        LOGGER.info("WBIG Test started");
        return "Process started";
    }

    public String receivedDebitAuthorization (@RequestBody DebitAuthorization daInfo, @PathVariable ("id") String wbig_processInstanceId) throws SQLException {

        LOGGER.info("Controller WBIG ProcessInstanceId: " + wbig_processInstanceId);

        //correlation specification via message name "DebitAuthorizationMessage". This needs to be inserted as message name for catching event in bpmn-model.
        runtimeService.createMessageCorrelation("DebitAuthorizationMessage")
                .processInstanceVariableEquals("wbig_processInstanceId", wbig_processInstanceId)
                .setVariable("IBAN", daInfo.getIBAN())
                .processInstanceId(wbig_processInstanceId)
                .correlate();


        LOGGER.info("DebitAuthorization received");
        return "Process started";
    }

}
