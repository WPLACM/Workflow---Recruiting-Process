package org.example.controller;

import camundajar.impl.com.google.gson.JsonObject;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.example.entity.Job_Opening;
import org.example.entity.Job_Profile;
import org.example.model.Application;
import org.example.model.JobOpeningReceiver;
import org.example.repository.Job_Opening_Repository;
import org.example.repository.Job_Profile_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/job-openings")
public class PublishJobOpeningController {
    @Autowired
    private Job_Opening_Repository repository;

    @Autowired
    private Job_Profile_Repository job_profile_repository;
    
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllEntries(Model model){
        List<Job_Opening> jobopenings = repository.findAllByOrderByJobOpeningIdDesc();  //Newest job openings
        //return ResponseEntity.ok(jobopenings);
        int i=0;
        if(jobopenings.get(0)!=null){
            //model.addAttribute(jobopenings.get(0).getJob_profile().)  //todo: change relation between Jop opening information and job profile
        }


        return "index";
    }
    //called by "Publish job opening" service task via http-request
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody JobOpeningReceiver job_opening) throws Exception{
        //Integer i = job_opening.getTemp();
        java.util.Date utildate = new java.util.Date();
        java.sql.Date date = new java.sql.Date(utildate.getTime());
        String insert_query =
                "INSERT INTO Job_Opening (FK_Job_Profile_ID,opening_Date) VALUES (?,?)";
        Connection con = DriverManager.getConnection("jdbc:h2:./camunda-db", "sa", "sa");
        PreparedStatement statement = con.prepareStatement(insert_query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, job_opening.getJobProfileId());
        statement.setDate(2, date);
        Integer index = statement.executeUpdate();
        System.out.println("insert job opening id:");
        System.out.println(index);
        return ResponseEntity.ok(job_opening);
    }

    @PostMapping("/job-opening")
    public String openingSubmit(@ModelAttribute org.example.model.Application application) {
        Integer id = 1;
        JsonObject app = new JsonObject() ;   //candidate_master_data
        app.addProperty("messageName", "ApplicationReceived");

        JsonObject correlationKeys = new JsonObject();
        correlationKeys.addProperty("openingId", id);   //hard-coded
        app.add("correlationKeys", correlationKeys);

        JsonObject processVariables = new JsonObject();
        processVariables.addProperty("first_name", application.getFirstName());
        processVariables.addProperty("last_name", application.getLastName());
        processVariables.addProperty("email", application.getEmail());
        processVariables.addProperty("birth_date", application.getBirthDate());
        processVariables.addProperty("gender", application.getGender());

        app.add("processVariables", processVariables);

        String candidate_master_data = app.toString();

        System.out.print("Candidate Master data" + candidate_master_data);

        HttpConnector http = Connectors.getConnector(HttpConnector.ID);
        http.createRequest()
                .post()
                .url("http://wfm-group-2.uni-muenster.de:80/engine-rest/message")
                .contentType("application/json")
                .payload(candidate_master_data)
                .execute();
        return "jobOpening";    //change return view to something useful
    }

    @GetMapping("/job-opening")
    public String viewJobOpening(@RequestParam(name="id", required=false, defaultValue="1") String id, Model model) {
        Job_Opening current = repository.findByJobOpeningId(Integer.parseInt(id));
        if(current==null){
            return "index";
        }
        Job_Profile profile = current.getJob_profile();
        //Adding Job_Opening specific variables
        model.addAttribute("jobOpeningId", id);
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.ENGLISH);
        String openingDate = dateFormat.format(current.getOpeningDate());
        String deadline = dateFormat.format(profile.getJob_opening_information().getDeadline());     //probably throws error
        model.addAttribute("deadline",deadline);
        model.addAttribute("openingDate", openingDate);
        //Adding Job_Profile text to model
        model.addAttribute("jobProfile", profile.getJobProfile());
         //Add job_opening_information to model
        model.addAttribute("jobTitle", profile.getJob_opening_information().getJob_title());
        model.addAttribute("qualifications", profile.getJob_opening_information().getRequired_qualifications());
        model.addAttribute("salary", profile.getJob_opening_information().getSalary());
        model.addAttribute("jobLocation", profile.getJob_opening_information().getJob_location());

        //expose application model to be filled with user form entries
        model.addAttribute("application", new Application());
        return "jobOpening";
    }
}
