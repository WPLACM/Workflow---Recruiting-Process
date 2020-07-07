package org.example.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.example.model.DebitAuthorization;

import java.sql.SQLException;
import java.util.logging.Logger;

@RestController
//creates "mailbox" to send to, relative to root path
@RequestMapping("/Billing")
public class Controller_DA {

    private final java.util.logging.Logger LOGGER = Logger.getLogger(Controller_NumberOfCandidates.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    // specifes mailbox path, {id} to correlate with specific process instance
    @PostMapping(path = "/DA/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String receiveDebitAuthorization (@RequestBody DebitAuthorization daInfo, @PathVariable ("id") String wbig_processInstanceId) throws SQLException {

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
