package org.example.model;

public class CancelOrder {
    private String WBIG_processInstanceID;

    public CancelOrder(String WBIG_processInstanceID){
        this.WBIG_processInstanceID = WBIG_processInstanceID;
    }

    public String getWBIG_processInstanceID() {
        return WBIG_processInstanceID;
    }

    public void setWBIG_processInstanceID(String WBIG_processInstanceID) {
        this.WBIG_processInstanceID = WBIG_processInstanceID;
    }

    public CancelOrder(){
    }
}
