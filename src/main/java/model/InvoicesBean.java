package model;

import util.BeansInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Simple java bean domain which represents Invoice. It implements BeanInterface which added to make collection of
InvoicesBean comparable.
 */

public class InvoicesBean implements BeansInterface {

    private String number;
    private float amount;
    private String date;
    /*The field represents date in format "dd-mm-yyyy"*/
    private String dateFormatted;
    /*The filed matches auto-filled column "due_date" in "invoices"-table. "due_date" column set 30 days difference from "date"*/
    private String dueDate;
    /*The field represents dueDate in format "dd-mm-yyyy"*/
    private String dueDateFormatted;
    /*The field set one of the 3 possible state: "paid", "overdue", "- - -"*/
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
