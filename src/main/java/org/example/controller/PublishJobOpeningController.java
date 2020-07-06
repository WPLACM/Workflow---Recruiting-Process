package org.example.controller;

import org.example.entity.Job_Opening;
import org.example.repository.Job_Opening_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Job_Opening job_opening){
        job_opening = repository.save(job_opening);
        return ResponseEntity.ok(job_opening);
    }

    @GetMapping("/job-opening")
    public String greeting(@RequestParam(name="id", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        //return "job-opening.thml";
        return "CheckoutForm";
    }
}
