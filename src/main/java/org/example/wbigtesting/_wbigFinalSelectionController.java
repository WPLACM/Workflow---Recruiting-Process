package org.example.wbigtesting;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.example.DemoDataGenerator;
import org.example.entity.Application;
import org.example.entity.ApplicationMessage;
import org.example.entity.ApplicationMessageList;
import org.example.model.JobOpeningInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/wbig")
public class _wbigFinalSelectionController {
    private final java.util.logging.Logger LOGGER = Logger.getLogger(Controller.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping(path = "/wbig_cvs/{wplacmid}/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String continueCVProcess(@RequestBody ApplicationMessageList payload, @PathVariable ("id") String wbig_processInstanceId, @PathVariable ("wplacmid") String wplacm_processInstanceId) {

        //Get the WPLACM Prozess Instance ID
        ApplicationMessage message = payload.getApplicationList().get(0);
        //At the moment null, not set TODO see class SendFinalSelectionDelegate
        //String wplacm_processInstanceId = message.getWplacm_process_instance_id();

        runtimeService.createMessageCorrelation("SomeCVs")
                //.processInstanceVariableEquals("wplacm_processInstanceId", wplacm_processInstanceId)
                .setVariable("wplacm_processInstanceId", wplacm_processInstanceId)
                //optinal: add further set variables here
                .processInstanceId(wbig_processInstanceId)
                .correlate();

        System.out.println("RECEIVED CVS");

        //LOGGER.info("WEBIG SomeCVs started");

        return "cvs received";
    }
}
