package com.example.futurebankgrupo1;

import java.lang.reflect.Array;

public class Transaction extends Account {
    private int id;
    private String transactionDate;
    private String appointmentDate;
    private int value;
    private String transactionType;
    private String direction; //Array [4] = [ PIX, TRANSFER, TRANSFER, ACCOUNT_DEBIT ]
    private String KeyPixType; //Array [4] = [ PHONE, CPF, EMAIL, RANDOM ]
    private String KeyPixValue;

    //Classe Account
    private Account sourceAccount;
    private Account destinationAccount;

    /*Account sourceAccount = new Account();
    Account destinationAccount = new Account();*/

    public Transaction(int id, String agency, String number, String bankNumber, double yieldRate, double limit,
                       double maintenanceFee, String accountType, int user_id, boolean emailNotification,
                       boolean smsNotification, int id1, String transactionDate, String appointmentDate, int value,
                       String transactionType, String direction, String keyPixType, String keyPixValue,
                       Account sourceAccount, Account destinationAccount) {

        super(id, agency, number, bankNumber, yieldRate, limit, maintenanceFee, accountType, user_id, emailNotification, smsNotification);
        this.id = id1;
        this.transactionDate = transactionDate;
        this.appointmentDate = appointmentDate;
        this.value = value;
        this.transactionType = transactionType;
        this.direction = direction;
        KeyPixType = keyPixType;
        KeyPixValue = keyPixValue;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getKeyPixType() {
        return KeyPixType;
    }

    public void setKeyPixType(String keyPixType) {
        KeyPixType = keyPixType;
    }

    public String getKeyPixValue() {
        return KeyPixValue;
    }

    public void setKeyPixValue(String keyPixValue) {
        KeyPixValue = keyPixValue;
    }
}
