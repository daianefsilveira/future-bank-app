package com.example.futurebankgrupo1;

public class Card {

    private String userName;
    private String number;
    private String expirationDate;
    private String securityCode;
    private boolean isBlocked;
    private String password;
    private double maxLimit;
    private double currentLimit;
    private double availableLimit;
    private double usageFee;
    private double dailyLimit;

    private String creditCardType; //array [ COMMON, SUPER, PREMIUM ]
    private String flag; //array [ VISA, MASTERCARD, ELO, AMERICAN_EXPRESS, HIPERCARD, OTHER ]

    public Card() {

    }

    public Card(String userName, String number, String expirationDate, String securityCode,
                boolean isBlocked, String password, double maxLimit, double currentLimit,
                double availableLimit, double usageFee, double dailyLimit, String creditCardType, String flag) {
        this.userName = userName;
        this.number = number;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.isBlocked = isBlocked;
        this.password = password;
        this.maxLimit = maxLimit;
        this.currentLimit = currentLimit;
        this.availableLimit = availableLimit;
        this.usageFee = usageFee;
        this.dailyLimit = dailyLimit;
        this.creditCardType = creditCardType;
        this.flag = flag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(double maxLimit) {
        this.maxLimit = maxLimit;
    }

    public double getCurrentLimit() {
        return currentLimit;
    }

    public void setCurrentLimit(double currentLimit) {
        this.currentLimit = currentLimit;
    }

    public double getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(double availableLimit) {
        this.availableLimit = availableLimit;
    }

    public double getUsageFee() {
        return usageFee;
    }

    public void setUsageFee(double usageFee) {
        this.usageFee = usageFee;
    }

    public double getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(double dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}

