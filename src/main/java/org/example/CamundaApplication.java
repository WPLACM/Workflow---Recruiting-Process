package org.example;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication("WPLACM")
public class CamundaApplication {
  public static void main(String... args) {
    SpringApplication.run(CamundaApplication.class, args);
  }

  //Gotta figure out how this is called. Taken from github invoice example - InvoiceProcessApplication
  @PostDeploy
  public void startFirstProcess(ProcessEngine processEngine) {
    createUsers(processEngine);
  }
  private void createUsers(ProcessEngine processEngine) {
    // create demo users
    new DemoDataGenerator().createUsers(processEngine);
  }
}
