//package org.example.Database;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Date;
//
//public class Opening {
//
//    private String WBIG_processInstanceID;
//    private String opening_name;
//    private Integer open_spots_initial;
//    private Integer open_spots_remaining;
//    private Double salary;
//    private String job_title;
//    private String job_description;
//    private String required_qualifications;
//    private String additional_information;
//    private Date deadline;
//    private double rewardPerAcceptance;
//    private String job_location;
//    private Integer working_hours;
//
//    public Opening(String WBIG_processInstanceID, String opening_name, Integer open_spots_initial, Integer open_spots_remaining, Double salary, String job_title, String job_description, String required_qualifications, String additional_information, Date deadline, double rewardPerAcceptance, String job_location, Integer working_hours) {
//        this.WBIG_processInstanceID = WBIG_processInstanceID;
//        this.opening_name = opening_name;
//        this.open_spots_initial = open_spots_initial;
//        this.open_spots_remaining = open_spots_remaining;
//        this.salary = salary;
//        this.job_title = job_title;
//        this.job_description = job_description;
//        this.required_qualifications = required_qualifications;
//        this.additional_information = additional_information;
//        this.deadline = deadline;
//        this.rewardPerAcceptance = rewardPerAcceptance;
//        this.job_location = job_location;
//        this.working_hours = working_hours;
//    }
//
//    public String getWBIG_processInstanceID() {
//        return WBIG_processInstanceID;
//    }
//
//    public void setWBIG_processInstanceID(String WBIG_processInstanceID) {
//        this.WBIG_processInstanceID = WBIG_processInstanceID;
//    }
//
//    public String getOpening_name() {
//        return opening_name;
//    }
//
//    public void setOpening_name(String opening_name) {
//        this.opening_name = opening_name;
//    }
//
//    public Integer getOpen_spots_initial() {
//        return open_spots_initial;
//    }
//
//    public void setOpen_spots_initial(Integer open_spots_initial) {
//        this.open_spots_initial = open_spots_initial;
//    }
//
//    public Integer getOpen_spots_remaining() {
//        return open_spots_remaining;
//    }
//
//    public void setOpen_spots_remaining(Integer open_spots_remaining) {
//        this.open_spots_remaining = open_spots_remaining;
//    }
//
//    public Double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(Double salary) {
//        this.salary = salary;
//    }
//
//    public String getJob_title() {
//        return job_title;
//    }
//
//    public void setJob_title(String job_title) {
//        this.job_title = job_title;
//    }
//
//    public String getJob_description() {
//        return job_description;
//    }
//
//    public void setJob_description(String job_description) {
//        this.job_description = job_description;
//    }
//
//    public String getRequired_qualifications() {
//        return required_qualifications;
//    }
//
//    public void setRequired_qualifications(String required_qualifications) {
//        this.required_qualifications = required_qualifications;
//    }
//
//    public String getAdditional_information() {
//        return additional_information;
//    }
//
//    public void setAdditional_information(String additional_information) {
//        this.additional_information = additional_information;
//    }
//
//    public Date getDeadline() {
//        return deadline;
//    }
//
//    public void setDeadline(Date deadline) {
//        this.deadline = deadline;
//    }
//
//    public double getRewardPerAcceptance() {
//        return rewardPerAcceptance;
//    }
//
//    public void setRewardPerAcceptance(double rewardPerAcceptance) {
//        this.rewardPerAcceptance = rewardPerAcceptance;
//    }
//
//    public String getJob_location() {
//        return job_location;
//    }
//
//    public void setJob_location(String job_location) {
//        this.job_location = job_location;
//    }
//
//    public Integer getWorking_hours() {
//        return working_hours;
//    }
//
//    public void setWorking_hours(Integer working_hours) {
//        this.working_hours = working_hours;
//    }
//
//    public Opening(){
//
//    }
//
//    public void add() throws SQLException {
//        Connection connection = DB_utility.getConnection();
//        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO public.job_openings(WBIG_processInstanceID,opening_name, open_spots_initial, salary, job_title, job_description, required_qualifications, additional_information, deadline, rewardperacceptance, job_location, working_hours) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
//        pstmt.setString(1, this.getWBIG_processInstanceID());
//        pstmt.setString(2, this.getOpening_name());
//        pstmt.setInt(3, this.getOpen_spots_initial());
//        pstmt.setDouble(4, this.getSalary());
//        pstmt.setString(5,this.getJob_title());
//        pstmt.setString(6,this.getJob_description());
//        pstmt.setString(7,this.getRequired_qualifications());
//        pstmt.setString(8,this.getAdditional_information());
//        pstmt.setDate(9,new java.sql.Date(this.getDeadline().getTime()));
//        pstmt.setDouble(10,this.getRewardPerAcceptance());
//        pstmt.setString(11,this.getJob_location());
//        pstmt.setInt(12,this.getWorking_hours());
//        pstmt.executeUpdate();
//        pstmt.close();
//        connection.close();
//        System.out.println("ProcessVariables: added new DB entry for opening");
//    }
//    public void update() throws SQLException {
//        Connection connection = DB_utility.getConnection();
//        PreparedStatement pstmt = connection.prepareStatement("UPDATE public.job_openings SET opening_name=?, open_spots_initial=?, salary=?, job_title=?, job_description=?, required_qualifications=?, additional_information=?, deadline=?, rewardperacceptance=?, job_location=?, working_hours=? WHERE wbig_processinstanceid=?");
//        pstmt.setString(1, this.getOpening_name());
//        pstmt.setInt(2, this.getOpen_spots_initial());
//        pstmt.setDouble(3, this.getSalary());
//        pstmt.setString(4,this.getJob_title());
//        pstmt.setString(5,this.getJob_description());
//        pstmt.setString(6,this.getRequired_qualifications());
//        pstmt.setString(7,this.getAdditional_information());
//        pstmt.setDate(8,new java.sql.Date(this.getDeadline().getTime()));
//        pstmt.setDouble(9,this.getRewardPerAcceptance());
//        pstmt.setString(10,this.getJob_location());
//        pstmt.setInt(11,this.getWorking_hours());
//        pstmt.setString(12, this.getWBIG_processInstanceID());
//        pstmt.executeUpdate();
//        pstmt.close();
//        connection.close();
//        System.out.println("ProcessVariables: updated DB entry for opening");
//    }
//}
//
//
