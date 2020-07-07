package org.example.model;

public class NoApplicationsReceivedMessage {
    private String wbig_process_id;
    private String WPLACM_processInstanceID;
    private String time_stamp;
    private String textmessage;

    public NoApplicationsReceivedMessage(String wbig_process_id, String WPLACM_processInstanceID, String time_stamp, String textmessage) {
        this.time_stamp = time_stamp;
        this.WPLACM_processInstanceID = WPLACM_processInstanceID;
        this.time_stamp = time_stamp;
        this.textmessage = textmessage;
    }

    public String getWPLACM_processInstanceID() {
        return WPLACM_processInstanceID;
    }

    public void setWPLACM_processInstanceID(String WBIG_processInstanceID) {
        this.WPLACM_processInstanceID = WBIG_processInstanceID;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getTextmessage() {
        return textmessage;
    }

    public void setTextmessage(String message) {
        this.textmessage = message;
    }
}
