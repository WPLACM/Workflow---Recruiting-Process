package org.example.model;

public class NoApplicationsReceivedMessage {
    private String wbig_process_id;
    private String time_stamp;
    private String textmessage;

    public NoApplicationsReceivedMessage(String wbig_process_id, String time_stamp, String textmessage) {
        this.wbig_process_id = wbig_process_id;
        this.time_stamp = time_stamp;
        this.textmessage = textmessage;
    }

    public String getWbig_process_id() {
        return wbig_process_id;
    }

    public void setWbig_process_id(String wbig_process_id) {
        this.wbig_process_id = wbig_process_id;
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
