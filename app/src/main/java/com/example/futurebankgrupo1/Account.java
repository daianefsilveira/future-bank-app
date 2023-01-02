package com.example.futurebankgrupo1;

public class Account {
    private int id;
    private String agency;
    private String number;
    private String bankNumber;
    private double yieldRate;
    private double limit;
    private double maintenanceFee;
    private String accountType;
    private int User_id;
    //Notificações
    private boolean emailNotification = true;
    private boolean smsNotification = true;

    public Account() {
    }

    public Account(int id, String agency, String number, String bankNumber, double yieldRate, double limit, double maintenanceFee,
                   String accountType, int user_id, boolean emailNotification, boolean smsNotification) {
        this.id = id;
        this.agency = agency;
        this.number = number;
        this.bankNumber = bankNumber;
        this.yieldRate = yieldRate;
        this.limit = limit;
        this.maintenanceFee = maintenanceFee;
        this.accountType = accountType;
        User_id = user_id;
        this.emailNotification = emailNotification;
        this.smsNotification = smsNotification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public double getYieldRate() {
        return yieldRate;
    }

    public void setYieldRate(double yieldRate) {
        this.yieldRate = yieldRate;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(double maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public boolean isEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public boolean isSmsNotification() {
        return smsNotification;
    }

    public void setSmsNotification(boolean smsNotification) {
        this.smsNotification = smsNotification;
    }
}
