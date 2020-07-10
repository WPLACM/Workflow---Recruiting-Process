package org.example.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.example.model.CancelOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.logging.Logger;

@RestController
//creates "mailbox" to send to, relative to root path
@RequestMapping("/Billing")
public class CancelOrderController {

    private final java.util.logging.Logger LOGGER = Logger.getLogger(CandidatesPlacedController.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    // specifes mailbox path, {id} to correlate with specific process instance
    @PostMapping(path = "/CancelOrder/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String continueBillingProcess (@RequestBody int orderInfodummy, @PathVariable ("id") String wplacm_processInstanceId) throws SQLException {



        //correlation specification via message name "OrderCanceledMessage". This needs to be inserted as message name for catching event in bpmn-model.
        runtimeService.createMessageCorrelation("OrderCancelledMessage")
                .processInstanceId(wplacm_processInstanceId)
                //.processInstanceVariableEquals("wbig_processInstanceId", wbig_processInstanceId)
                //.processInstanceVariableEquals("wplacm_processInstanceId", wbig_processInstanceId)
                //.setVariable("wbig_processInstanceId", wbig_processInstanceId)
                //.processInstanceId(candidateInfo.getWBIG_processInstanceID())
                .correlate();

        System.out.println("Order cancel received");
        return wplacm_processInstanceId;
    }

}