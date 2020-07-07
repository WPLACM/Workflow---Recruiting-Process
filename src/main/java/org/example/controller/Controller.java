package org.wbig.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wbig.WEPLACM_dummy.WeplacmVariables;
import org.wbig.database.Application;
import org.wbig.database.ApplicationList;
import org.wbig.database.Invoice;
import org.wbig.model.ProcessVariables;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@RestController
//creates "mailbox" to send to, relative to root path
@RequestMapping("/wbig")
public class Controller {

    private final java.util.logging.Logger LOGGER = Logger.getLogger(Controller.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    // specifes mailbox path, {id} to correlate with specific process instance
    @PostMapping(path = "/wbig_cvs/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String continueCVProcess (@RequestBody ApplicationList payload, @PathVariable ("id") String wplacm_processInstanceId) throws SQLException {

        LOGGER.info("Controller WPLACM ProcessInstanceId: " + wplacm_processInstanceId);

        //corralation specification via message nanme "SomeCVs". This needs to be inserted as message name for catching event in bpmn-model.
        runtimeService.createMessageCorrelation("SomeCVs")
                .processInstanceVariableEquals("wplacm_processInstanceId", wplacm_processInstanceId)
                //optinal: add further set variables here
                .correlate();

        LOGGER.info("Controller: Trying to insert into DB");

        // optinaol: read variables from data-object
        List<Application> applications = payload.getApplicationlist();

        for (int i=0; i<applications.size(); i++){
            //TODO: Refer to real ProcessInstanceId
            //optional: write data to database
            applications.get(i).add();
        }

        LOGGER.info("Controller: added new DB entry");

        LOGGER.info("WEBIG SomeCVs started");
        return "Process started";
    }


}
