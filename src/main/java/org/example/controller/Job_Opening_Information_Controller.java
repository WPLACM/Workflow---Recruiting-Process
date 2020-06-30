package org.example.controller;

import org.example.dto.Job_Opening_Information_Request;
import org.example.entity.Client_Company;
import org.example.repository.Client_Company_Repository;
import org.example.repository.Job_Opening_Information_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Example one can try out with postman
@RestController
public class Job_Opening_Information_Controller {
    @Autowired
    private Client_Company_Repository clientCompanyRepository;
    @Autowired
    private Job_Opening_Information_Repository jobOpeningInformationRepository;

    @PostMapping("/sendJobOpeningInformation")
    public Client_Company sendJobOpeningInformation(@RequestBody Job_Opening_Information_Request request){
        return clientCompanyRepository.save(request.getClient_company());
    }

    @GetMapping("/findAllJobOpenings")
    public List<Client_Company> findAllClientCompanies(){
        return clientCompanyRepository.findAll();
    }
}
