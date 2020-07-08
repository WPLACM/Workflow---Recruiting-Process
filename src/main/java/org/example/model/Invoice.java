package org.example.model;
import java.util.Date;

public class Invoice {
    private String WPLACM_processInstanceID;
    private Double payment_information_acceptances;
    private String invoice_date;
    private String tax_id;
    private String address_recipient;
    private String address_sender;
    private Integer number_of_acceptances;
    private String opening_id;
    private String opening_name;
    private Double gross;
    private Double net;
    private Double sales_tax;
    private String invoice_id;

    public Invoice(String WPLACM_processInstanceID, String invoice_id, Double payment_information_acceptances, String invoice_date, String tax_id, String address_recipient,
                   String address_sender, Integer number_of_acceptances, String opening_id, String opening_name, Double gross,
                   Double net, Double sales_tax) {
        this.WPLACM_processInstanceID = WPLACM_processInstanceID;
        this.invoice_id = invoice_id;
        this.payment_information_acceptances = payment_information_acceptances;
        this.invoice_date = invoice_date;
        this.tax_id = tax_id;
        this.address_recipient = address_recipient;
        this.address_sender = address_sender;
        this.number_of_acceptances = number_of_acceptances;
        this.opening_id = opening_id;
        this.opening_name = opening_name;
        this.gross = gross;
        this.net = net;
        this.sales_tax = sales_tax;
    }

    public String getWPLACM_processInstanceID() {
        return WPLACM_processInstanceID;
    }

    public void setWPLACM_processInstanceID(String WBIG_processInstanceID) {
        this.WPLACM_processInstanceID = WBIG_processInstanceID;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public double getPayment_information_acceptances() {
        return payment_information_acceptances;
    }

    public void setPayment_information_acceptances(Double payment_information_acceptances) {
        this.payment_information_acceptances = payment_information_acceptances;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getAddress_recipient() {
        return address_recipient;
    }

    public void setAddress_recipient(String address_recipient) {
        this.address_recipient = address_recipient;
    }

    public String getAddress_sender() {
        return address_sender;
    }

    public void setAddress_sender(String address_sender) {
        this.address_sender = address_sender;
    }

    public Integer getNumber_of_acceptances() {
        return number_of_acceptances;
    }

    public void setNumber_of_acceptances(Integer number_of_acceptances) {
        this.number_of_acceptances = number_of_acceptances;
    }

    public String getOpening_id() {
        return opening_id;
    }

    public void setOpening_id(String opening_id) {
        this.opening_id = opening_id;
    }

    public String getOpening_name() {
        return opening_name;
    }

    public void setOpening_name(String opening_name) {
        this.opening_name = opening_name;
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public Double getNet() {
        return net;
    }

    public void setNet(Double net) {
        this.net = net;
    }

    public Double getSales_tax() {
        return sales_tax;
    }

    public void setSales_tax(Double sales_tax) {
        this.sales_tax = sales_tax;
    }

    public Invoice(){

    }
}
