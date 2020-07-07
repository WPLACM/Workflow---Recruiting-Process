package org.example.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class NumberOfCandidates {

    private String WBIG_processInstanceID;
    private Integer number_of_acceptances;
    private Integer payment_info;

    public NumberOfCandidates(String WBIG_processInstanceID, Integer number_of_acceptances, Integer payment_info) {
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

    public Integer getPayment_info() {
        return payment_info;
    }

    public void setPayment_info(Integer payment_info) {
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


