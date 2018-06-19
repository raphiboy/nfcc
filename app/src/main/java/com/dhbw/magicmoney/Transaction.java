package com.dhbw.magicmoney;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Transaction {

    private String emailSender;
    private String emailReceiver;
    private Date date;

    public Transaction(String emailSender, String emailReceiver, double volume, Date date) {
        this.emailSender = emailSender;
        this.emailReceiver = emailReceiver;
        this.volume = volume;
        this.date = date;
    }

    private double volume;

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public String getEmailReceiver() {
        return emailReceiver;
    }

    public void setEmailReceiver(String emailReceiver) {
        this.emailReceiver = emailReceiver;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getDate() {

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMANY);
        return dateFormat.format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public String getEURBalance(){
        return NumberFormat.getCurrencyInstance(Locale.GERMANY).format((this.getVolume()));
    }

}
