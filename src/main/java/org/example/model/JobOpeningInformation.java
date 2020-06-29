package org.example.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class JobOpeningInformation {
    /*
    Attributes
     */
    private String openingId;
    private int openSpots;
    private int salary;
    private String jobTitle;
    private String openingName;
    private String jobDescription;
    private String requiredQualifications;
    private String additionalInformation;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    private int paymentInformationAcceptances;
    private String jobLocation;
    private int workingHours;

    /*
    Getter and Setter
     */
    public String getOpeningId() {
        return openingId;
    }

    public void setOpeningId(String opening_ID) {
        this.openingId = opening_ID;
    }

    public int getOpenSpots() {
        return openSpots;
    }

    public void setOpenSpots(int openSpots) {
        this.openSpots = openSpots;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getOpeningName() {
        return openingName;
    }

    public void setOpeningName(String openingName) {
        this.openingName = openingName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getRequiredQualifications() {
        return requiredQualifications;
    }

    public void setRequiredQualifications(String requiredQualifications) {
        this.requiredQualifications = requiredQualifications;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getPaymentInformationAcceptances() {
        return paymentInformationAcceptances;
    }

    public void setPaymentInformationAcceptances(int paymentInformationAcceptances) {
        this.paymentInformationAcceptances = paymentInformationAcceptances;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }
}
