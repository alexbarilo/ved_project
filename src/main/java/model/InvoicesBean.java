package model;

import util.BeansInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoicesBean implements BeansInterface {

    private String number;
    private float amount;
    private String date;
    private String dateFormatted;
    private String dueDate;
    private String dueDateFormatted;
    private String paymentState;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getDateFormatted() {

        Date dateObj;
        String dateToFormat = this.getDate();

        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            dateObj = ymdFormat.parse(dateToFormat);
            dateFormatted = dmyFormat.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormatted;
    }

    public String getDueDateFormatted() {
        Date dateObj;
        String dateToFormat = this.getDueDate();

        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            dateObj = ymdFormat.parse(dateToFormat);
            dueDateFormatted = dmyFormat.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dueDateFormatted;
    }

}
