package org.example.controller;

import org.example.entity.Job_Opening;
import org.example.entity.Job_Profile;
import org.example.repository.Job_Opening_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/job-openings")
public class PublishJobOpeningController {
    @Autowired
    private Job_Opening_Repository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEntries(){
        //List<Job_Opening> jobopenings = repository.findAll();
        List<Job_Opening> jobopenings = repository.findAllByOrderByJobOpeningIdDesc();
        return ResponseEntity.ok(jobopenings);
    }

    //called by "Publish job opening" service task via http-request
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Job_Opening job_opening){
        job_opening = repository.save(job_opening);
        return ResponseEntity.ok(job_opening);
    }


    @GetMapping("/job-opening")
    public String viewJobOpening(@RequestParam(name="id", required=false, defaultValue="1") String id, Model model) {
        Job_Opening current = repository.findByJobOpeningId(Integer.parseInt(id));
        Job_Profile profile = current.getJob_profile();
        model.addAttribute("id", id);
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
        String openingDate = dateFormat.format(current.getOpeningDate());
        String deadline = dateFormat.format(current.getDeadline());
        model.addAttribute("deadline",deadline);
        model.addAttribute("openingDate", openingDate);

        //todo:  add Job_Profile attributes

        return "CheckoutForm";
    }
}
