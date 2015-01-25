package model;

import util.BeansInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentsBean implements BeansInterface {

    private float amount;
    private String date;
    private String dateFormatted;

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
}
