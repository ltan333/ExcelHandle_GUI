package com.gui.minitask_gui;

import java.util.Date;

public class TotalAll {
    private Date date;
    private double totalNotTip;
    private double amount;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TotalAll() {
    }

    public double getTotalNotTip() {
        return totalNotTip;
    }

    public void setTotalNotTip(double totalNotTip) {
        this.totalNotTip = totalNotTip;
    }

    public TotalAll(Date date, double amount,double totalNotTip) {
        this.date = date;
        this.amount = amount;
        this.totalNotTip = totalNotTip;
    }
}
