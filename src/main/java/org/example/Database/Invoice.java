package org.example.Database;
import java.util.Date;

public class Invoice {
    private double payment_information_acceptances;
    private Date invoice_date;
    private String tax_id;
    private String address_recipient;
    private String address_sender;
    private int number_of_acceptances;
    private String opening_id;
    private String opening_name;
    private double gross;
    private double net;
    private double sales_tax;

    public Invoice(double payment_information_acceptances, Date invoice_date, String tax_id, String address_recipient,
                   String address_sender, int number_of_acceptances, String opening_id, String opening_name, double gross,
                   double net, double sales_tax) {
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

    public double getPayment_information_acceptances() {
        return payment_information_acceptances;
    }

    public void setPayment_information_acceptances(double payment_information_acceptances) {
        this.payment_information_acceptances = payment_information_acceptances;
    }

    public Date getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(Date invoice_date) {
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

    public int getNumber_of_acceptances() {
        return number_of_acceptances;
    }

    public void setNumber_of_acceptances(int number_of_acceptances) {
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

    public double getGross() {
        return gross;
    }

    public void setGross(double gross) {
        this.gross = gross;
    }

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public double getSales_tax() {
        return sales_tax;
    }

    public void setSales_tax(double sales_tax) {
        this.sales_tax = sales_tax;
    }
}
