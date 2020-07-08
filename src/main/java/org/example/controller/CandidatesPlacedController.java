package org.example.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.example.model.NumberOfCandidates;

import java.sql.SQLException;
import java.util.logging.Logger;

@RestController
//creates "mailbox" to send to, relative to root path
@RequestMapping("/Billing")
public class CandidatesPlacedController {

    private final java.util.logging.Logger LOGGER = Logger.getLogger(CandidatesPlacedController.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    // specifes mailbox path, {id} to correlate with specific process instance
    @PostMapping(path = "/CandidatesPlaced/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String continueBillingProcess (@RequestBody NumberOfCandidates candidateInfo, @PathVariable ("id") String wbig_processInstanceId) throws SQLException {



        //correlation specification via message name "CandidatesPlacedMessage". This needs to be inserted as message name for catching event in bpmn-model.
        runtimeService.createMessageCorrelation("CandidatesPlacedMessage")
                .processInstanceVariableEquals("WBIG_process_ID", wbig_processInstanceId)
                .setVariable("number_of_acceptances", candidateInfo.getNumber_of_acceptances())
                .setVariable("payment_info", candidateInfo.getPayment_info())
                .setVariable("wbig_processInstanceId", wbig_processInstanceId)
                //.processInstanceId(candidateInfo.getWBIG_processInstanceID())
                .correlate();

        System.out.println("Amount of placed Candidates received");
        return wbig_processInstanceId;
    }

}
