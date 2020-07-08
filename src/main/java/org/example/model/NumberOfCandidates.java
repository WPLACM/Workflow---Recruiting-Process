package org.example.model;
public class NumberOfCandidates {

    private String WBIG_processInstanceID;
    private Integer number_of_acceptances;
    private Double payment_info;

    public NumberOfCandidates(String WBIG_processInstanceID, Integer number_of_acceptances, Double payment_info) {
        this.WBIG_processInstanceID = WBIG_processInstanceID;
        this.number_of_acceptances = number_of_acceptances;
        this.payment_info = payment_info;

    }

    public String getWBIG_processInstanceID() {
        return WBIG_processInstanceID;
    }

    public void setWBIG_processInstanceID(String WBIG_processInstanceID) {
        this.WBIG_processInstanceID = WBIG_processInstanceID;
    }

    public Double getPayment_info() {
        return payment_info;
    }

    public void setPayment_info(Double payment_info) {
        this.payment_info = payment_info;
    }

    public Integer getNumber_of_acceptances() {
        return number_of_acceptances;
    }

    public void setNumber_of_acceptances(Integer number_of_acceptances) {
        this.number_of_acceptances = number_of_acceptances;
    }
    public NumberOfCandidates(){

    }
}


