package com.gui.minitask_gui;

public class SalaryDetail {
    private int week;
    private double cash,cre_debt,venmo,zelle,giftTakeIn,giftSell;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getCre_debt() {
        return cre_debt;
    }

    public void setCre_debt(double cre_debt) {
        this.cre_debt = cre_debt;
    }

    public double getVenmo() {
        return venmo;
    }

    public void setVenmo(double venmo) {
        this.venmo = venmo;
    }

    public double getZelle() {
        return zelle;
    }

    public void setZelle(double zelle) {
        this.zelle = zelle;
    }

    public double getGiftTakeIn() {
        return giftTakeIn;
    }

    public void setGiftTakeIn(double giftTakeIn) {
        this.giftTakeIn = giftTakeIn;
    }

    public double getGiftSell() {
        return giftSell;
    }

    public void setGiftSell(double giftSell) {
        this.giftSell = giftSell;
    }



    public double getTotal(){
        return cash+cre_debt+venmo+zelle+giftSell+giftTakeIn;
    }
}
