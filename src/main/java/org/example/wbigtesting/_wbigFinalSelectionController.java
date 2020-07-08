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

    @PostMapping(path = "/wbig_cvs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String continueCVProcess(@RequestBody ApplicationMessageList payload, String wplacm_processInstanceId) {

        LOGGER.info("Controller WPLACM ProcessInstanceId: " + wplacm_processInstanceId);

        //corralation specification via message nanme "SomeCVs". This needs to be inserted as message name for catching event in bpmn-model.
        runtimeService.createMessageCorrelation("SomeCVs")
                .processInstanceVariableEquals("wplacm_processInstanceId", wplacm_processInstanceId)
                //optinal: add further set variables here
                .correlate();

        List<ApplicationMessage> applicationList  = payload.getApplicationList();

        System.out.println("RECEIVED CVS");

        LOGGER.info("WEBIG SomeCVs started");

        return "cvs received";
    }
}
