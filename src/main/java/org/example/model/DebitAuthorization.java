package org.example.model;

public class DebitAuthorization {
    //private String WBIG_processInstanceID;
    private String IBAN;

    public DebitAuthorization(String IBAN) { //String IBAN){
        //this.WBIG_processInstanceID = WBIG_processInstanceID;
        this.IBAN = IBAN;

    }
    /*
    public String getWBIG_processInstanceID() {
        return WBIG_processInstanceID;
    }

    public void setWBIG_processInstanceID(String WBIG_processInstanceID) {
        this.WBIG_processInstanceID = WBIG_processInstanceID;
    }
    */


    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public DebitAuthorization(){

    }
}
